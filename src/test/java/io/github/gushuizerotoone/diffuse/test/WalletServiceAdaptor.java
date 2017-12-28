package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.ServiceAdaptor;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ActionType;
import io.github.gushuizerotoone.diffuse.core.servicepoint.NextAction;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;

import java.util.HashMap;
import java.util.Map;

public class WalletServiceAdaptor implements ServiceAdaptor {

  @Override
  public ServicePointState normalProcess(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("walletId", "Test-WalletId-1");
    map.put("status", "SUCCESS");

    ServicePointState serviceState = sagaContext.getServiceState(getName());
    serviceState.fillData(ServicePointStatus.COMPLETED, new NextAction(ActionType.ROUTINE), map);
    return serviceState;
  }

  @Override
  public ServicePointState compensate(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("walletId", "Test-WalletId-1");
    map.put("status", "SUCCESS");

    ServicePointState serviceState = sagaContext.getServiceState(getName());
    serviceState.fillData(ServicePointStatus.COMPENSATED, new NextAction(ActionType.ROUTINE), map);
    return serviceState;
  }

}
