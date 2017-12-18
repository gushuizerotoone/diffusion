package io.github.gushuizerotoone.diffuse.core;

public class SagaBuilder {

  private Saga saga;
  private ServicePoint lastServicePoint;

  public SagaBuilder sagaContext(SagaContext sagaContext) {
    saga = new Saga(sagaContext);
    return this;
  }

  public SagaBuilder addService(ServiceAdaptor serviceAdaptor) {
    ServicePoint servicePoint = new CompositeServicePoint(saga.getSagaContext(), serviceAdaptor);
    if (saga.getFirstServicePoint() == null) {
      saga.setFirstServicePoint(servicePoint);
      lastServicePoint = servicePoint;
    }

    lastServicePoint.setNext(servicePoint);
    lastServicePoint = servicePoint;

    saga.getSagaContext().appendServiceName(serviceAdaptor.getName());
    return this;
  }

  public SagaBuilder redoPolicy(RedoPolicy redoPolicy) {
    saga.setRedoPolicy(redoPolicy);
    saga.getSagaContext().fillRedoPolicy(redoPolicy);
    return this;
  }

  public Saga toSaga() {
    return saga;
  }

  public SagaBuilder rebuild(SagaContext sagaContext) {
    return null;
  }

}
