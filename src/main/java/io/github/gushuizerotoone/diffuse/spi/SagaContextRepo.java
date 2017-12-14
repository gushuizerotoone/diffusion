package io.github.gushuizerotoone.diffuse.spi;

import io.github.gushuizerotoone.diffuse.core.SagaContext;

import java.util.List;
import java.util.Optional;

public interface SagaContextRepo {
  void saveSaga(SagaContext sagaContext);
  Optional<List<SagaContext>> getTimeoutSagaContext(long timeout);
}
