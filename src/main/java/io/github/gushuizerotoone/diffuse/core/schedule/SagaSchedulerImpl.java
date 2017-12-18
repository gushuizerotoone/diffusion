package io.github.gushuizerotoone.diffuse.core.schedule;

import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaBuilder;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

import java.util.List;

public class SagaSchedulerImpl implements SagaScheduler {
  private SagaContextRepo sagaContextRepo;

  public SagaSchedulerImpl(SagaContextRepo sagaContextRepo) {
    this.sagaContextRepo = sagaContextRepo;
  }

  @Override
  public void processTimeoutSagas(Long timeoutSeconds) {
    List<SagaContext> sagas = sagaContextRepo.getTimeoutSagaContext(timeoutSeconds);
    sagas.stream()
            .forEach(sagaContext -> prepareRedo(sagaContext.getSagaId()));
  }

  @Override
  public void prepareRedo(String sagaId) {
    // TODO: queue impl, now invoke redo instantly
    SagaContext sagaContext = sagaContextRepo.getSagaContext(sagaId);
    SagaBuilder sb = new SagaBuilder();

    Saga saga = sb.rebuild(sagaContext);
    saga.redo();
  }
}
