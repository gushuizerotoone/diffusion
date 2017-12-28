package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaScheduler;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaSchedulerImpl;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;

import java.util.ArrayList;
import java.util.Objects;

public class Saga implements Redoable<Saga> {

  private SagaContext sagaContext;

  private RedoPolicy redoPolicy;

  private ServicePoint firstServicePoint;
  private SagaScheduler sagaScheduler;

  public Saga(SagaContext sagaContext) {
    this.sagaContext = sagaContext;

    SagaFactory sagaFactory = SagaFactoryImpl.getInstance(); // TODO
    sagaScheduler = sagaFactory.getSagaScheduler(SagaSchedulerImpl.class); // TODO
  }

  public SagaStatus process() {
    try {
      firstServicePoint.normalProcess();
      SagaStatus sagaStatus = sagaContext.normalizeSagaStatus();

      // if not COMPLETED, will retry or compensate
      if (sagaStatus != SagaStatus.COMPLETED) {
        return redoAndGetStatus();
      }

      return sagaStatus;
    } catch (Exception e) {
      e.printStackTrace(); // TODO, replace by log
      return redoAndGetStatus();
    }
  }

  private SagaStatus redoAndGetStatus() {
    Saga saga = sagaScheduler.schedulerRedo(sagaContext.getSagaId());
    return saga.getSagaContext().normalizeSagaStatus();
  }

  public SagaContext getSagaContext() {
    return sagaContext;
  }


  @Override
  public Saga redo() {
    Objects.requireNonNull(redoPolicy, "Can not find redoPolicy: " + toShortString());

    Strategy strategy = redoPolicy.decideStrategy(new ArrayList<>(sagaContext.getServiceStates().values()));
    if (strategy == null) {
      throw new RuntimeException("Can not redo sagaContext: " + toShortString());
    }

    try {
      strategy.forward(firstServicePoint);
    } catch (Exception e) {
      e.printStackTrace(); // TODO, replace by log
    }

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
