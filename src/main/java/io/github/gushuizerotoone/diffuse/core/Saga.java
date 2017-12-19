package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class Saga implements Redoable<Saga> {

  private SagaContext sagaContext;

  private RedoPolicy redoPolicy;

  private ServicePoint firstServicePoint;

  public Saga(SagaContext sagaContext) {
    this.sagaContext = sagaContext;
  }

  public SagaStatus process() {
    firstServicePoint.normalProcess();
    return status();
  }

  public SagaContext getSagaContext() {
    return sagaContext;
  }

  public SagaStatus status() {
    Map<String, ServicePointState> serviceStates = sagaContext.getServiceStates();

    Map<ServicePointStatus, Integer> serviceStatusCount = serviceStates.values()
            .stream()
            .collect(Collectors.toMap(s -> s.getStatus(), s -> 1, (v1, v2) -> v1 + v2));

    if (serviceStatusCount.getOrDefault(ServicePointStatus.COMPENSATING, 0) > 0 || serviceStatusCount.getOrDefault(ServicePointStatus.PREPARE_COMPENSATE, 0) > 0) {
      return SagaStatus.COMPENSATING;
    }

    if (serviceStatusCount.getOrDefault(ServicePointStatus.COMPLETED, 0) == serviceStates.size()) {
      return SagaStatus.COMPLETED;
    }

    if (serviceStatusCount.getOrDefault(ServicePointStatus.COMPENSATED, 0) == serviceStates.size()) {
      return SagaStatus.COMPENSATED;
    }

    return SagaStatus.PROCESSING;
  }

  @Override
  public Saga redo() {
    Objects.requireNonNull(redoPolicy, "Can not find redoPolicy: " + toShortString());

    Strategy strategy = redoPolicy.getStrategy(new ArrayList<>(sagaContext.getServiceStates().values()));
    if (strategy == null) {
      throw new RuntimeException("Can not redo sagaContext: " + toShortString());
    }

    strategy.forward(firstServicePoint);
    return this;
  }

  public ServicePoint getFirstServicePoint() {
    return firstServicePoint;
  }

  public void setFirstServicePoint(ServicePoint firstServicePoint) {
    this.firstServicePoint = firstServicePoint;
  }

  public RedoPolicy getRedoPolicy() {
    return redoPolicy;
  }

  public void setRedoPolicy(RedoPolicy redoPolicy) {
    this.redoPolicy = redoPolicy;
  }

  public String toShortString() {
    return String.format("[%s - %s]", sagaContext.getName(), sagaContext.getSagaId());
  }

}
