package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public class CompositeServicePoint implements ServicePoint {

  private String name;
  private SagaContext sagaContext;
  private ServicePoint nextServicePoint;
  private ServiceAdaptor serviceAdaptor;

  public CompositeServicePoint(String name, SagaContext sagaContext, ServiceAdaptor serviceAdaptor) {
    this.sagaContext = sagaContext;
    this.name = name;
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
    serviceAdaptor.normalProcess(sagaContext);
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
    serviceAdaptor.compensate(sagaContext);
    return sagaContext;
  }

  @Override
  public void fillRedoStates(List<ServicePointRedoState> redoStates) {
    redoStates.add(serviceAdaptor.getRedoState(sagaContext));
    if (!isLeaf()) {
      nextServicePoint.fillRedoStates(redoStates);
    }
  }

  @Override
  public ServicePointState getState() {
    return serviceAdaptor.getState(sagaContext);
  }
}
