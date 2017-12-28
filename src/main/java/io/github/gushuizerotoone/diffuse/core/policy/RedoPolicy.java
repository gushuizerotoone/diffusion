package io.github.gushuizerotoone.diffuse.core.policy;

import io.github.gushuizerotoone.diffuse.core.CompensateStrategy;
import io.github.gushuizerotoone.diffuse.core.RetryStrategy;
import io.github.gushuizerotoone.diffuse.core.Strategy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ActionType;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public interface RedoPolicy {

  default Strategy decideStrategy(List<ServicePointState> states) {
    Map<ActionType, Integer> actionToCountMap = states.stream()
            .collect(Collectors.toMap(state -> state.getNextAction().getActionType(), state -> 1, (a1, a2) -> a1 + a2));

    if (actionToCountMap.getOrDefault(ActionType.TO_COMPENSATE, 0) > 0) { // compensate priority first
      return new CompensateStrategy();
    }

    if (actionToCountMap.getOrDefault(ActionType.TO_RETRY, 0) > 0) { // redo priority later
      return new RetryStrategy();
    }

    return findStrategy(states);
  }

  Strategy findStrategy(List<ServicePointState> states);
}
