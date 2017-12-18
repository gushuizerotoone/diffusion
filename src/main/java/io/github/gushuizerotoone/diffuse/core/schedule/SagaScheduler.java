package io.github.gushuizerotoone.diffuse.core.schedule;

public interface SagaScheduler {
  void processTimeoutSagas(Long timeoutSeconds);
  void prepareRedo(String sagaId);
}
