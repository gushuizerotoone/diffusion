package io.github.gushuizerotoone.diffuse.core;

public interface ServiceAdaptor {
  default String getName() {
    return this.getClass().getSimpleName();
  }

  default ServicePointRedoState getRedoState(final SagaContext sagaContext) {
    return ServicePointRedoState.ALL;
  }

  ServiceResponse normalProcess(final SagaContext sagaContext);

  ServiceResponse compensate(final SagaContext sagaContext);

  ServicePointState getState(final SagaContext sagaContext);
}
