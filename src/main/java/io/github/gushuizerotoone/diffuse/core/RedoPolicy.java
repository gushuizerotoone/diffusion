package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;

import java.util.List;

public interface RedoPolicy {
  Strategy getStrategy(List<ServicePointRedoStatus> redoStates);
}
