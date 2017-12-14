package io.github.gushuizerotoone.diffuse.core;

public interface SagaBuilder {

  Saga addServicePoint();

  Saga statePolicy();

  Saga rebuild();

}
