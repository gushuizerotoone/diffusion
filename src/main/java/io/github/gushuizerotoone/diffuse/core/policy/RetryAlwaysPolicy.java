package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.RetryStrategy;
import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.List;

public class RetryAlwaysPolicy implements RedoPolicy {

  @Override
  public Strategy findStrategy(List<ServicePointState> states) {
    return new RetryStrategy();
  }

}
