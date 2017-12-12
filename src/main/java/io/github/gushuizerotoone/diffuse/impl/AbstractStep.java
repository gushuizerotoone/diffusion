package io.github.gushuizerotoone.diffuse.impl;

import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.Step;

public abstract class AbstractStep<CT extends SagaContext> implements Step<CT> {

  private String name;

  public AbstractStep(String name) {
    this.name = name;
  }

  public AbstractStep() {
  }

  @Override
  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }
}
