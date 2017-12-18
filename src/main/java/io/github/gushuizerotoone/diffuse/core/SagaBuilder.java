package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

import java.util.Comparator;
import java.util.Objects;

public class SagaBuilder {

  private Saga saga;
  private ServicePoint lastServicePoint;
  private SagaContextRepo sagaContextRepo;
  private SagaFactory sagaFactory = new SagaFactoryImpl(); // TODO

  public SagaBuilder sagaContext(SagaContext sagaContext) {
    saga = new Saga(sagaContext);
    return this;
  }

  public SagaBuilder sagaContextRepository(SagaContextRepo sagaContextRepo) {
    this.sagaContextRepo = sagaContextRepo;
    return this;
  }

  public SagaBuilder addService(Class<? extends ServiceAdaptor> serviceAdaptorClazz) {
    Objects.nonNull(saga);
    Objects.nonNull(sagaContextRepo);

    ServiceAdaptor serviceAdaptor = sagaFactory.getServiceAdaptor(serviceAdaptorClazz);
    generateAndSetServicePoint(serviceAdaptor);

    saga.getSagaContext().appendService(serviceAdaptorClazz);
    return this;
  }

  private void generateAndSetServicePoint(ServiceAdaptor serviceAdaptor) {
    ServicePoint servicePoint = new CompositeServicePoint(saga.getSagaContext(), serviceAdaptor, sagaContextRepo);
    if (saga.getFirstServicePoint() == null) {
      saga.setFirstServicePoint(servicePoint);
      lastServicePoint = servicePoint;
    }

    lastServicePoint.setNext(servicePoint);
    lastServicePoint = servicePoint;
  }

  public SagaBuilder redoPolicy(Class<? extends RedoPolicy> redoPolicyClazz) {
    Objects.nonNull(saga);
    Objects.nonNull(sagaContextRepo);

    RedoPolicy redoPolicy = sagaFactory.getRedoPolicy(redoPolicyClazz);
    saga.setRedoPolicy(redoPolicy);
    saga.getSagaContext().fillRedoPolicy(redoPolicy);
    return this;
  }

  public Saga rebuild(SagaContext sagaContext) {
    sagaContext(sagaContext);

    // generate ServiceAdaptors
    sagaContext.getServiceStates().values()
            .stream()
            .sorted(Comparator.comparing(ServicePointState::getOrder))
            .map(servicePointState -> sagaFactory.getServiceAdaptor(servicePointState.getClassName()))
            .forEach(serviceAdaptor -> generateAndSetServicePoint(serviceAdaptor));

    // generate RedoPolicy
    redoPolicy(sagaFactory.getRedoPolicy(sagaContext.getRedoPolicyClassName()).getClass());

    return toSaga();
  }

  public Saga toSaga() {
    return saga;
  }

}
