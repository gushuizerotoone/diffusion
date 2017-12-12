package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public interface SagaDefinition<CT extends SagaContext> {
  String getName();
  List<Step<CT>> getSteps();
}
