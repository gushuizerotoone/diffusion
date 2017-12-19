package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.List;

public interface RedoPolicy {
  Strategy getStrategy(List<ServicePointState> states);
}
