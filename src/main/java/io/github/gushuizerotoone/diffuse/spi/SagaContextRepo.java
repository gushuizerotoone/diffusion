package io.github.gushuizerotoone.diffuse.spi;

import io.github.gushuizerotoone.diffuse.core.SagaContext;

import java.util.List;
import java.util.Optional;

public interface SagaContextRepo {
  void saveSagaContext(SagaContext sagaContext);
  SagaContext getSagaContext(String sagaId);
  List<SagaContext> getTimeoutSagaContext(long timeoutSeconds);
}
