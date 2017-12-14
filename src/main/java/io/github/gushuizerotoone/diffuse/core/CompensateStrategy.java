package io.github.gushuizerotoone.diffuse.core;

public class CompensateStrategy implements Strategy {
  @Override
  public void forward(ServicePoint firstServicePoint) {
    firstServicePoint.compensate();
  }
}
