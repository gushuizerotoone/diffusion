package io.github.gushuizerotoone.diffuse.core;

public enum ServicePointRedoState {
  NONE,
  ONLY_RETRYABLE,
  ONLY_COMPENSABLE,
  ALL,
}
