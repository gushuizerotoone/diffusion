package io.github.gushuizerotoone.diffuse.core;

public class RetryStrategy implements Strategy {

  @Override
  public void forward(ServicePoint firstServicePoint) {
    firstServicePoint.normalProcess();
  }
}
