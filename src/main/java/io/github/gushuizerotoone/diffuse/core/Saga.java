package io.github.gushuizerotoone.diffuse.core;

public interface Saga<CT extends SagaContext> {
  void start();
}
