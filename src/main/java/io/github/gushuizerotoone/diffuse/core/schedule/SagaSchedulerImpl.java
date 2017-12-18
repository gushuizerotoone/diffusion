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
            .forEach(sagaContext -> immediatelyRedo(sagaContext.getSagaId()));
  }

  @Override
  public Saga immediatelyRedo(String sagaId) {
    SagaContext sagaContext = sagaContextRepo.getSagaContext(sagaId);
    SagaBuilder sb = new SagaBuilder();
    Saga saga = sb.sagaContextRepository(sagaContextRepo)
            .rebuild(sagaContext);
    return saga.redo();
  }
}
