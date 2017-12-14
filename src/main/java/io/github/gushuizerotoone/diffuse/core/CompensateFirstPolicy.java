package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public class CompensateFirstPolicy implements RedoPolicy {
  @Override
  public Strategy getStrategy(List<ServicePointRedoStatus> redoStates) {
    // TODO: thomas
    return new CompensateStrategy();
  }
}
