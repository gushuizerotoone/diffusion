package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.ServiceAdaptor;
import io.github.gushuizerotoone.diffuse.core.ServicePointRedoState;
import io.github.gushuizerotoone.diffuse.core.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.ServiceResponse;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 2017/12/14.
 */
public class OrderServiceAdaptor implements ServiceAdaptor {

  @Override
  public ServiceResponse normalProcess(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("orderId", "Test-OrderId-1");
    map.put("status", "SUCCESS");

    return new ServiceResponse(map);
  }

  @Override
  public ServiceResponse compensate(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("orderId", "Test-OrderId-1");
    map.put("status", "SUCCESS");

    return new ServiceResponse(map);
  }

  @Override
  public ServicePointState getState(SagaContext sagaContext) {
    return ServicePointState.COMPLETED;
  }
}
