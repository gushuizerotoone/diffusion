package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public class CompositeServicePoint implements ServicePoint {

  private String name;
  private SagaContext sagaContext;
  private ServicePoint nextServicePoint;
  private ServiceAdaptor serviceAdaptor;

  public CompositeServicePoint(SagaContext sagaContext, ServiceAdaptor serviceAdaptor) {
    this.sagaContext = sagaContext;
    this.name = serviceAdaptor.getName();
    this.serviceAdaptor = serviceAdaptor;
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
    ServicePointState servicePointState = serviceAdaptor.normalProcess(sagaContext);
    sagaContext.fill(getName(), servicePointState);

    if (!isLeaf()) {
      nextServicePoint.normalProcess();
    }
    return sagaContext;
  }

  @Override
  public SagaContext compensate() {
    if (!isLeaf()) {
      nextServicePoint.compensate();
    }

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
