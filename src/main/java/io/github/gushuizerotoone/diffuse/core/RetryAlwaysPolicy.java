package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public class RetryAlwaysPolicy implements RedoPolicy {

  @Override
  public Strategy getStrategy(List<ServicePointRedoStatus> redoStates) {
    return new RetryStrategy();
  }

}
