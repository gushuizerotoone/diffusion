package io.github.gushuizerotoone.diffuse.core;

public class SagaBuilder {

  public static Saga saga(SagaContext sagaContext) {
    return new Saga(sagaContext);
  }

  public static Saga rebuild(SagaContext sagaContext) {
    return null;
  }

}
