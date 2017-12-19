package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.CompensateStrategy;
import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.List;

public class CompensateFirstPolicy implements RedoPolicy {
  @Override
  public Strategy getStrategy(List<ServicePointState> states) {
    // TODO: thomas
    return new CompensateStrategy();
  }
}
