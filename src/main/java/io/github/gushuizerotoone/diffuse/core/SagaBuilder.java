package io.github.gushuizerotoone.diffuse.core;

public class SagaBuilder {

  public Saga saga(SagaContext sagaContext) {
    return new Saga(sagaContext);
  }

  Saga rebuild(SagaContext sagaContext) {
    return null;
  }

}
