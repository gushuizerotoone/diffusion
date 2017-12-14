package io.github.gushuizerotoone.diffuse.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Saga implements Redoable<SagaContext> {

  private SagaContext sagaContext;

  private RedoPolicy redoPolicy;

  private ServicePoint firstServicePoint;

  private ServicePoint lastServicePoint;

  public Saga(SagaContext sagaContext) {
    this.sagaContext = sagaContext;
  }

  public Saga addService(ServiceAdaptor serviceAdaptor) {
    ServicePoint servicePoint = new CompositeServicePoint(sagaContext, serviceAdaptor);
    if (firstServicePoint == null) {
      firstServicePoint = servicePoint;
      lastServicePoint = servicePoint;
    }

    lastServicePoint.setNext(servicePoint);
    lastServicePoint = servicePoint;

    sagaContext.appendServiceName(serviceAdaptor.getName());
    return this;
  }

  public Saga redoPolicy(RedoPolicy redoPolicy) {
    this.redoPolicy = redoPolicy;
    sagaContext.fillRedoPolicy(redoPolicy);
    return this;
  }

  public SagaStatus process() {
    firstServicePoint.normalProcess();
    return status(sagaContext);
  }

  public SagaContext getSagaContext() {
    return sagaContext;
  }
  private SagaStatus status(SagaContext sagaContext) {
    // TODO: prepare to implement another way, map-reduce
    Map<String, ServicePointState> serviceStates = sagaContext.getServiceStates();
    Map<ServicePointStatus, Integer> serviceStatusCount = new HashMap<>(8);

    Arrays.stream(ServicePointStatus.values())
            .forEach(status -> serviceStatusCount.put(status, 0));

    serviceStates.values()
            .stream()
            .forEach(state -> serviceStatusCount.put(state.getStatus(), serviceStatusCount.get(state.getStatus()) + 1));

    if (serviceStatusCount.get(ServicePointStatus.PROCESSING) > 0) {
      return SagaStatus.PROCESSING;
    }

    if (serviceStatusCount.get(ServicePointStatus.COMPENSATING) > 0) {
      return SagaStatus.COMPENSATING;
    }

    if (serviceStatusCount.get(ServicePointStatus.COMPLETED) == serviceStates.size()) {
      return SagaStatus.COMPLETED;
    }

    if (serviceStatusCount.get(ServicePointStatus.COMPENSATED) == serviceStates.size()) {
      return SagaStatus.COMPENSATED;
    }

    return SagaStatus.PROCESSING;
  }

  @Override
  public SagaContext redo() {
    Objects.requireNonNull(redoPolicy, "can not find redoPolicy: " + toShortString());

    List<ServicePointRedoStatus> redoStates = new ArrayList<>();
    firstServicePoint.fillRedoStates(redoStates);

    Strategy strategy = redoPolicy.getStrategy(redoStates);
    if (redoPolicy.getStrategy(redoStates) == null) {
      throw new RuntimeException("can not redo saga: " + toShortString());
    }

    strategy.forward(firstServicePoint);
    return sagaContext;
  }

  public String toShortString() {
    return String.format("[%s - %s]", sagaContext.getName(), sagaContext.getSagaId());
  }

}
