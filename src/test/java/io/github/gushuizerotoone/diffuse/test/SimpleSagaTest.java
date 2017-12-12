package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaDefinitionService;
import io.github.gushuizerotoone.diffuse.core.SagaStatus;
import io.github.gushuizerotoone.diffuse.impl.AbstractStep;
import io.github.gushuizerotoone.diffuse.impl.DefaultSagaDefinition;
import io.github.gushuizerotoone.diffuse.impl.SagaCreator;
import io.github.gushuizerotoone.diffuse.impl.SimpleSagaContext;
import org.junit.Assert;
import org.junit.Test;

public class SimpleSagaTest {

  private SagaCreator creator;
  private SagaDefinitionService definitionService;

  private static void dummyOp(long millis) {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private void registerDefinition() {
    DefaultSagaDefinition<SimpleSagaContext> def = new DefaultSagaDefinition<>();
    def.setName("OrderFlow");
    def.addStep(new AbstractStep<SimpleSagaContext>("Money") {
      @Override
      public void act(SimpleSagaContext context) {
        dummyOp(100);
        context.getValueMap().put("MoneyId", 200);
      }
      @Override
      public void compensate(SimpleSagaContext context) {
        dummyOp(100);
      }
    });
    def.addStep(new AbstractStep<SimpleSagaContext>("Shipment") {
      @Override
      public void act(SimpleSagaContext context) {
        dummyOp(100);
        String shipmentId = context.getValueMap().get("MoneyId").toString() + "-300";
        context.getValueMap().put("ShipmentId", shipmentId);
      }
      @Override
      public void compensate(SimpleSagaContext context) {
        dummyOp(100);
      }
    });
    definitionService.register(def);
  }

  @Test
  public void testMain() {
    SimpleSagaContext context = new SimpleSagaContext();
    Saga<SimpleSagaContext> saga = creator.createSaga("OrderFlow", context, "O_300");
    saga.start();
    Assert.assertEquals(saga.getStatus(), SagaStatus.COMPLETED);
    Assert.assertEquals(saga.getCurrentContext().getValueMap().get("ShipmentId"), "200-300");
  }
}
