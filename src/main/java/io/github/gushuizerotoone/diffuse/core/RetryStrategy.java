package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;

public class RetryStrategy implements Strategy {

  @Override
  public void forward(ServicePoint firstServicePoint) {
    firstServicePoint.normalProcess();
  }
}
