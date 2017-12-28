package io.github.gushuizerotoone.diffuse.core.schedule;

import io.github.gushuizerotoone.diffuse.core.Saga;

public interface SagaScheduler {
  void processTimeoutSagas(Long timeoutSeconds);
  Saga schedulerRedo(String sagaId);
}
