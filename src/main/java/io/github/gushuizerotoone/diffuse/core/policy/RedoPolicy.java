package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;

import java.util.List;

public interface RedoPolicy {
  Strategy getStrategy(List<ServicePointRedoStatus> redoStates);
}
