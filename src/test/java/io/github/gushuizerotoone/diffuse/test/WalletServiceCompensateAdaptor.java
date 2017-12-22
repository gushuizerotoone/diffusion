package io.github.gushuizerotoone.diffuse.test;

import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.ServiceAdaptor;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointAction;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;

import java.util.HashMap;
import java.util.Map;

public class WalletServiceCompensateAdaptor implements ServiceAdaptor {

  @Override
  public ServicePointState normalProcess(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("walletId", "Test-WalletId-1");
    map.put("status", "SUCCESS");

    ServicePointState serviceState = sagaContext.getServiceState(getName());
    serviceState.getCurrentStatus().toPrepareCompensate();
    serviceState.fillContent(map);
    serviceState.setServicePointAction(ServicePointAction.DEPEND_ON_POLICY);
    return serviceState;
  }

  @Override
  public ServicePointState compensate(SagaContext sagaContext) {
    Map<String, Object> map = new HashMap<>();
    map.put("walletId", "Test-WalletId-1");
    map.put("status", "SUCCESS");

    ServicePointState serviceState = sagaContext.getServiceState(getName());
    serviceState.getCurrentStatus().toCompensated();
    serviceState.fillContent(map);
    return serviceState;
  }

}
