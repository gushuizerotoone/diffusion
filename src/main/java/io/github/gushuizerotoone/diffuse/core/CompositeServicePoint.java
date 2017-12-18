package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

import java.util.List;

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
  public ServicePoint getNext() {
    return nextServicePoint;
  }

  @Override
  public void setNext(ServicePoint servicePoint) {
    this.nextServicePoint = servicePoint;
  }

  @Override
  public SagaContext normalProcess() {
    ServicePointState state = sagaContext.getServiceState(getName());
    state.getCurrentStatus().toProcessing();
    sagaContextRepo.saveSagaContext(sagaContext);

    ServicePointState servicePointState = serviceAdaptor.normalProcess(sagaContext);
    sagaContext.fill(getName(), servicePointState);
    if (servicePointState.getStatus() != ServicePointStatus.COMPLETED) {
      return sagaContext;
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
    state.getCurrentStatus().toCompensating();
    sagaContextRepo.saveSagaContext(sagaContext);

    ServicePointState servicePointState = serviceAdaptor.compensate(sagaContext);
    sagaContext.fill(getName(), servicePointState);

    return sagaContext;
  }

  @Override
  public void fillRedoStates(List<ServicePointRedoStatus> redoStates) {
    redoStates.add(serviceAdaptor.getRedoState(sagaContext));
    if (!isLeaf()) {
      nextServicePoint.fillRedoStates(redoStates);
    }
  }

  @Override
  public ServicePointStatus getState() {
    return serviceAdaptor.getState(sagaContext);
  }
}
