package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.SagaFactory;
import io.github.gushuizerotoone.diffuse.core.SagaFactoryImpl;
import io.github.gushuizerotoone.diffuse.core.policy.CompensateAlwaysPolicy;
import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaBuilder;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaStatus;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaScheduler;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaSchedulerImpl;
import io.github.gushuizerotoone.diffuse.spi.InMemorySagaContextRepo;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;
import org.junit.Assert;
import org.junit.Test;

public class SimpleSagaTest {

  @Test
  public void testNormalProcess() {
    SagaFactory sagaFactory = SagaFactoryImpl.getInstance();
    SagaContextRepo sagaContextRepo = sagaFactory.getSagaContextRepo(InMemorySagaContextRepo.class);

    SagaContext sagaContext = new SagaContext("SAGA_ID_1", "mySaga");
    SagaBuilder sb = new SagaBuilder();
    Saga saga = sb.sagaContext(sagaContext)
            .sagaContextRepository(sagaContextRepo)
            .addService(OrderServiceAdaptor.class)
            .addService(WalletServiceAdaptor.class)
            .redoPolicy(CompensateAlwaysPolicy.class)
            .toSaga();

    SagaStatus sagaStatus = saga.process();
    System.out.println(sagaContext);

    Assert.assertEquals(SagaStatus.COMPLETED, sagaStatus);
    Assert.assertEquals("SUCCESS", sagaContext.getServiceStateValue(OrderServiceAdaptor.class.getSimpleName(), "status").get());
  }

  @Test
  public void testCompensate() {
    SagaFactory sagaFactory = SagaFactoryImpl.getInstance();

    SagaContextRepo sagaContextRepo = sagaFactory.getSagaContextRepo(InMemorySagaContextRepo.class);
    SagaContext sagaContext = new SagaContext("SAGA_ID_2", "mySaga");
    SagaBuilder sb = new SagaBuilder();
    Saga saga = sb.sagaContext(sagaContext)
            .sagaContextRepository(sagaContextRepo)
            .addService(OrderServiceAdaptor.class)
            .addService(WalletServiceCompensateAdaptor.class) // wallet compensate
            .redoPolicy(CompensateAlwaysPolicy.class)
            .toSaga();

    SagaStatus sagaStatus = saga.process();
    System.out.println(sagaContext);
    Assert.assertEquals(SagaStatus.COMPENSATING, sagaStatus);

    SagaScheduler sagaScheduler = sagaFactory.getSagaScheduler(SagaSchedulerImpl.class);
    saga = sagaScheduler.immediatelyRedo(sagaContext.getSagaId());

    System.out.println(saga.getSagaContext());
    Assert.assertEquals(SagaStatus.COMPENSATED, saga.normalizeStatus());
  }
}
