package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.ServiceAdaptor;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas on 2017/12/14.
 */
public class OrderServiceAdaptor implements ServiceAdaptor {

  @Override
  public ServicePointState normalProcess(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("orderId", "Test-OrderId-1");
    map.put("status", "SUCCESS");

    return new ServicePointState(ServicePointStatus.COMPLETED, getName(), map);
  }

  @Override
  public ServicePointState compensate(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("orderId", "Test-OrderId-1");
    map.put("status", "SUCCESS");

    return new ServicePointState(ServicePointStatus.COMPLETED, getName(), map);
  }

  @Override
  public ServicePointStatus getState(SagaContext sagaContext) {
    return ServicePointStatus.COMPLETED;
  }
}
