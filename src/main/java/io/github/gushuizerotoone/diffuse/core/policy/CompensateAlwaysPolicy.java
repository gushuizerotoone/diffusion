package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.CompensateStrategy;
import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.List;

public class CompensateAlwaysPolicy implements RedoPolicy {
  @Override
  public Strategy findStrategy(List<ServicePointState> states) {
    return new CompensateStrategy();
  }
}
