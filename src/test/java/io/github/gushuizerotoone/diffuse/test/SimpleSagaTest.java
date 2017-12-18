package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.policy.CompensateAlwaysPolicy;
import io.github.gushuizerotoone.diffuse.core.policy.RetryAlwaysPolicy;
import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaBuilder;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaStatus;
import io.github.gushuizerotoone.diffuse.spi.InMemorySagaContextRepo;
import org.junit.Assert;
import org.junit.Test;

public class SimpleSagaTest {

  @Test
  public void testMain() {
    SagaContext sagaContext = new SagaContext("SAGA_ID_1", "mySaga");
    SagaBuilder sb = new SagaBuilder();
    Saga saga = sb.sagaContext(sagaContext)
            .sagaContextRepository(new InMemorySagaContextRepo())
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
    SagaContext sagaContext = new SagaContext("SAGA_ID_2", "mySaga");
    SagaBuilder sb = new SagaBuilder();
    Saga saga = sb.sagaContext(sagaContext)
            .sagaContextRepository(new InMemorySagaContextRepo())
            .addService(OrderServiceAdaptor.class)
            .addService(WalletServiceCompensateAdaptor.class) // wallet compensate
            .redoPolicy(CompensateAlwaysPolicy.class)
            .toSaga();

    SagaStatus sagaStatus = saga.process();
    System.out.println(sagaContext);

    saga = sb.rebuild(sagaContext);
    saga.redo();

    Assert.assertEquals(SagaStatus.COMPENSATING, sagaStatus);
  }
}
