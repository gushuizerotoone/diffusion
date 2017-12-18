package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.RetryAlwaysPolicy;
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
            .addService(new OrderServiceAdaptor())
            .addService(new WalletServiceAdaptor())
            .redoPolicy(new RetryAlwaysPolicy())
            .toSaga();

    SagaStatus sagaStatus = saga.process();
    System.out.println(sagaContext);

    Assert.assertEquals(SagaStatus.COMPLETED, sagaStatus);
    Assert.assertEquals("SUCCESS", sagaContext.getServiceStateValue(OrderServiceAdaptor.class.getSimpleName(), "status").get());
  }
}
