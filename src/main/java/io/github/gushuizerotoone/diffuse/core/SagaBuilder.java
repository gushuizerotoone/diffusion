package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

public class SagaBuilder {

  private Saga saga;
  private ServicePoint lastServicePoint;
  private SagaContextRepo sagaContextRepo;

  public SagaBuilder sagaContext(SagaContext sagaContext) {
    saga = new Saga(sagaContext);
    return this;
  }

  public SagaBuilder sagaContextRepository(SagaContextRepo sagaContextRepo) {
    this.sagaContextRepo = sagaContextRepo;
    return this;
  }

  public SagaBuilder addService(ServiceAdaptor serviceAdaptor) {
    ServicePoint servicePoint = new CompositeServicePoint(saga.getSagaContext(), serviceAdaptor, sagaContextRepo);
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
