package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public interface RedoPolicy {
  Strategy getStrategy(List<ServicePointRedoStatus> redoStates);
}
