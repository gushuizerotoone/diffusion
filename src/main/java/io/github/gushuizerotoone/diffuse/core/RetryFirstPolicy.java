package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public class RetryFirstPolicy implements RedoPolicy {

  @Override
  public Strategy getStrategy(List<ServicePointRedoState> redoStates) {
    // TODO: thomas, retry first, compensate last
    return new CompensateStrategy();
  }

}