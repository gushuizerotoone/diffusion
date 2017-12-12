package io.github.gushuizerotoone.diffuse.core;

public interface Step<CT extends SagaContext> {
  String getName();
  void act(CT context);
  void compensate(CT context);
}
