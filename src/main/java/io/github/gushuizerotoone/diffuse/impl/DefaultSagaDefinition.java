package io.github.gushuizerotoone.diffuse.impl;

import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaDefinition;
import io.github.gushuizerotoone.diffuse.core.Step;

import java.util.ArrayList;
import java.util.List;

public class DefaultSagaDefinition<CT extends SagaContext> implements SagaDefinition<CT> {

  private String name;
  private List<Step<CT>> steps = new ArrayList<>();

  @Override
  public String getName() {
    return name;
  }

  @Override
  public List<Step<CT>> getSteps() {
    return steps;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void addStep(Step step) {
    getSteps().add(step);
  }
}
