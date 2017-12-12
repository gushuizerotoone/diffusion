package io.github.gushuizerotoone.diffuse.spi;

import io.github.gushuizerotoone.diffuse.impl.SagaImpl;

public interface SagaRepo {
  void saveSaga(SagaImpl saga);
}
