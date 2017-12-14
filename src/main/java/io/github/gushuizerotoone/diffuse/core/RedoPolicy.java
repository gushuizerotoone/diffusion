package io.github.gushuizerotoone.diffuse.core;

public interface RedoPolicy {
  boolean test(ServicePointRedoState[] redoStates);
}
