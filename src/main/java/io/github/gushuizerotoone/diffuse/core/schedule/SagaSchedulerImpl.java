package io.github.gushuizerotoone.diffuse.core.schedule;

import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaBuilder;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaFactory;
import io.github.gushuizerotoone.diffuse.core.SagaFactoryImpl;
import io.github.gushuizerotoone.diffuse.spi.InMemorySagaContextRepo;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class SagaSchedulerImpl implements SagaScheduler {
  private SagaContextRepo sagaContextRepo;
  private SagaFactory sagaFactory = SagaFactoryImpl.getInstance(); // TODO
  private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(20);

  public SagaSchedulerImpl() {
    this.sagaContextRepo = sagaFactory.getSagaContextRepo(InMemorySagaContextRepo.class);
  }

  @Override
  public void processTimeoutSagas(Long timeoutSeconds) {
    List<SagaContext> sagas = sagaContextRepo.getTimeoutSagaContext(timeoutSeconds);
    sagas.stream()
            .forEach(sagaContext -> schedulerRedo(sagaContext.getSagaId()));
  }

  @Override
  public Saga schedulerRedo(String sagaId) {
    SagaContext sagaContext = sagaContextRepo.getSagaContext(sagaId);
    SagaBuilder sb = new SagaBuilder();
    Saga saga = sb.sagaContextRepository(sagaContextRepo)
            .rebuild(sagaContext);

    if (sagaContext.isRedoNow()) {
      return saga.redo();
    } else if (sagaContext.isRedoLater()) {
      executorService.scheduleWithFixedDelay(() -> saga.redo(), 0, sagaContext.getRedoDelay(), TimeUnit.SECONDS);
    }
    return saga;
  }

}
