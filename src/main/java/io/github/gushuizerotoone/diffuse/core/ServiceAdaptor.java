package io.github.gushuizerotoone.diffuse.core;

public interface ServiceAdaptor {
  SagaContext normalProcess(SagaContext sagaContext);
  SagaContext compensate(SagaContext sagaContext);
  ServicePointState getState(SagaContext sagaContext);
  ServicePointRedoState getRedoState(SagaContext sagaContext);
}
