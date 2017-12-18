package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.CompensateStrategy;
import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;

import java.util.List;

public class CompensateAlwaysPolicy implements RedoPolicy {
  @Override
  public Strategy getStrategy(List<ServicePointRedoStatus> redoStates) {
    return new CompensateStrategy();
  }
}
