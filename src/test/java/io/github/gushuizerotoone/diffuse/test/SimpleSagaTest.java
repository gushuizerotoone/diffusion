package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.RetryAlwaysPolicy;
import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaBuilder;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaStatus;
import org.junit.Assert;
import org.junit.Test;

public class SimpleSagaTest {

  @Test
  public void testMain() {
    SagaContext sagaContext = new SagaContext("SAGA_ID_1", "mySaga");

    SagaStatus sagaStatus = SagaBuilder.saga(sagaContext)
            .addService(new OrderServiceAdaptor())
            .addService(new WalletServiceAdaptor())
            .redoPolicy(new RetryAlwaysPolicy())
            .process();

    System.out.println(sagaContext);

    Assert.assertEquals(SagaStatus.COMPLETED, sagaStatus);
    Assert.assertEquals("SUCCESS", sagaContext.getServiceResponseValue(OrderServiceAdaptor.class.getSimpleName(), "status").get());
  }
}
