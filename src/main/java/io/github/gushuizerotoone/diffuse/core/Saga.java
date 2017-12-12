package io.github.gushuizerotoone.diffuse.core;

public interface Saga<CT extends SagaContext> {
  SagaStatus getStatus();
  CT getCurrentContext();
  void start();
}
