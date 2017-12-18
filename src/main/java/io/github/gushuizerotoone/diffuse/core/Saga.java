package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaScheduler;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaSchedulerImpl;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    if (serviceStatusCount.get(ServicePointStatus.COMPENSATING) > 0 || serviceStatusCount.get(ServicePointStatus.PREPARE_COMPENSATE) > 0) {
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
  public Saga redo() {
    Objects.requireNonNull(redoPolicy, "Can not find redoPolicy: " + toShortString());

    List<ServicePointRedoStatus> redoStates = new ArrayList<>();
    firstServicePoint.fillRedoStates(redoStates);

    Strategy strategy = redoPolicy.getStrategy(redoStates);
    if (redoPolicy.getStrategy(redoStates) == null) {
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
