package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.RetryAlwaysPolicy;
import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaBuilder;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import org.junit.Assert;
import org.junit.Test;

public class SimpleSagaTest {

  @Test
  public void testMain() {
    SagaContext sagaContext = new SagaContext("SAGA_ID_1", "mySaga");

    Saga saga = SagaBuilder.saga(sagaContext)
            .addService(new OrderServiceAdaptor())
            .addService(new WalletServiceAdaptor())
            .redoPolicy(new RetryAlwaysPolicy())
            .process();

    System.out.println(sagaContext);

    Assert.assertEquals("SUCCESS", sagaContext.getServiceResponseValue(OrderServiceAdaptor.class.getSimpleName(), "status").get());
  }
}
