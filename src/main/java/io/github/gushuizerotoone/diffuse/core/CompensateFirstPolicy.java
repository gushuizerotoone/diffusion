package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;

import java.util.List;

public class CompensateFirstPolicy implements RedoPolicy {
  @Override
  public Strategy getStrategy(List<ServicePointRedoStatus> redoStates) {
    // TODO: thomas
    return new CompensateStrategy();
  }
}
