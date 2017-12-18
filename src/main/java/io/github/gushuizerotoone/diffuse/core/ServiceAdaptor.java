package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointRedoStatus;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;

public interface ServiceAdaptor {
  default String getName() {
    return this.getClass().getSimpleName();
  }

  default ServicePointRedoStatus getRedoState(final SagaContext sagaContext) {
    return ServicePointRedoStatus.ALL;
  }

  ServicePointState normalProcess(final SagaContext sagaContext);

  ServicePointState compensate(final SagaContext sagaContext);

  ServicePointStatus getState(final SagaContext sagaContext);
}
