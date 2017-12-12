package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaDefinitionService;
import io.github.gushuizerotoone.diffuse.impl.AbstractStep;
import io.github.gushuizerotoone.diffuse.impl.DefaultSagaDefinition;
import io.github.gushuizerotoone.diffuse.impl.SagaCreator;
import io.github.gushuizerotoone.diffuse.impl.SimpleSagaContext;

public class SimpleSagaTest {

  private SagaCreator creator;
  private SagaDefinitionService definitionService;

  private static void sleepMillis(long millis) {
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
        sleepMillis(100);
        context.getValueMap().put("MoneyId", 200);
      }
      @Override
      public void compensate(SimpleSagaContext context) {
        sleepMillis(100);
      }
    });
    def.addStep(new AbstractStep<SimpleSagaContext>("Shipment") {
      @Override
      public void act(SimpleSagaContext context) {
        sleepMillis(100);
        String shipmentId = context.getValueMap().get("MoneyId").toString() + "-300";
        context.getValueMap().put("ShipmentId", shipmentId);
      }
      @Override
      public void compensate(SimpleSagaContext context) {
        sleepMillis(100);
      }
    });
    definitionService.register(def);
  }

  public void testMain() {
    SimpleSagaContext context = new SimpleSagaContext();
    Saga saga = creator.createSaga("OrderFlow", context, "O_300");
    saga.start();
  }
}
