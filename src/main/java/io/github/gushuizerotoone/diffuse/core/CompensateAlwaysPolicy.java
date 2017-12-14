package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public class CompensateAlwaysPolicy implements RedoPolicy {
  @Override
  public Strategy getStrategy(List<ServicePointRedoState> redoStates) {
    return new CompensateStrategy();
  }
}
