package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

public class CompositeServicePoint implements ServicePoint {

  private String name;
  private SagaContext sagaContext; // stateful
  private ServicePoint nextServicePoint;
  private ServiceAdaptor serviceAdaptor;
  private SagaContextRepo sagaContextRepo;

  public CompositeServicePoint(SagaContext sagaContext, ServiceAdaptor serviceAdaptor, SagaContextRepo sagaContextRepo) {
    this.sagaContext = sagaContext;
    this.name = serviceAdaptor.getName();
    this.serviceAdaptor = serviceAdaptor;
    this.sagaContextRepo = sagaContextRepo;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public boolean isLeaf() {
    return nextServicePoint == null;
  }

  @Override
  public void setNext(ServicePoint servicePoint) {
    this.nextServicePoint = servicePoint;
  }

  @Override
  public SagaContext normalProcess() {
    ServicePointState state = sagaContext.getServiceState(getName());
    if (state.getStatus() != ServicePointStatus.COMPLETED && state.getStatus() != ServicePointStatus.PROCESSING) {
      state.getCurrentStatus().toProcessing();
      sagaContextRepo.saveSagaContext(sagaContext);

      ServicePointState servicePointState = serviceAdaptor.normalProcess(sagaContext);
      sagaContext.setServiceState(getName(), servicePointState);
      if (servicePointState.getStatus() != ServicePointStatus.COMPLETED) {
        return sagaContext;
      }
    }

    if (!isLeaf()) {
      nextServicePoint.normalProcess();
    }
    return sagaContext;
  }


  @Override
  public SagaContext compensate() {
    if (!isLeaf()) {
      nextServicePoint.compensate();
      ServicePointState nextServicePointState = sagaContext.getServiceState(nextServicePoint.getName());
      if (nextServicePointState.getStatus() != ServicePointStatus.COMPENSATED) {
        return sagaContext;
      }
    }

    ServicePointState state = sagaContext.getServiceState(getName());
    if (state.getStatus() != ServicePointStatus.COMPENSATED && state.getStatus() != ServicePointStatus.COMPENSATING) {
      state.getCurrentStatus().toPrepareCompensate();
      state.getCurrentStatus().toCompensating();
      sagaContextRepo.saveSagaContext(sagaContext);

      ServicePointState servicePointState = serviceAdaptor.compensate(sagaContext);
      sagaContext.setServiceState(getName(), servicePointState);
    }

    return sagaContext;
  }

}
