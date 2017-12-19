package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

public interface ServiceAdaptor {

  default String getName() {
    return this.getClass().getSimpleName();
  }

  ServicePointState normalProcess(final SagaContext sagaContext);

  ServicePointState compensate(final SagaContext sagaContext);

}
