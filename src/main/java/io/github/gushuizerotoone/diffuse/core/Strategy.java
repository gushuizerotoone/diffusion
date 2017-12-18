package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePoint;

public interface Strategy {

  void forward(ServicePoint firstServicePoint);

}
