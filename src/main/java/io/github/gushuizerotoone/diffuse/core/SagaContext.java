package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SagaContext {
  private String sagaId;
  private String name;
  private Map<String, Object> sagaBaseMap;
  private Map<String, ServicePointState> serviceStates;
  private int order = 0;
  private Date lastModifyDate;

  private static final String REDO_POLICY = "_Redo_Policy";

  public SagaContext(String sagaId, String name) {
    this.sagaId = sagaId;
    this.name = name;
    sagaBaseMap = new ConcurrentHashMap<>(8);
    serviceStates = new ConcurrentHashMap<>(8);
    lastModifyDate = new Date();
  }

  public void appendServiceName(String name) {
    ServicePointState state = new ServicePointState(ServicePointStatus.PREPARE_PROCESS, name);
    state.setOrder(++order);

    serviceStates.put(name, state);
  }

  public void fillBase(Map<String, Object> elements) {
    sagaBaseMap.putAll(elements);
  }

  public void fillBase(String key, String value) {
    sagaBaseMap.put(key, value);
  }

  public void fillRedoPolicy(final RedoPolicy redoPolicy) {
    sagaBaseMap.put(REDO_POLICY, redoPolicy.getClass().getSimpleName());
  }

  public void fill(String serviceName, ServicePointState servicePointState) {
    ServicePointState old = serviceStates.get(serviceName);
    old.getContent().putAll(servicePointState.getContent());
    old.setDate(servicePointState.getDate());
    old.setStatus(servicePointState.getStatus());

    saveServiceState(serviceName, old);
  }

  private void saveServiceState(String serviceName, ServicePointState state) {
    serviceStates.put(serviceName, state);
    lastModifyDate = new Date();
  }

  public Optional<String> getServiceStateValue(String serviceName, String key) {
    Object value = getServiceState(serviceName).getContent().get(key);
    return Optional.ofNullable(value.toString());
  }

  public ServicePointState getServiceState(String serviceName) {
    return serviceStates.get(serviceName);
  }

  public String getSagaId() {
    return sagaId;
  }

  public void setSagaId(String sagaId) {
    this.sagaId = sagaId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Map<String, Object> getSagaBaseMap() {
    return sagaBaseMap;
  }

  public void setSagaBaseMap(Map<String, Object> sagaBaseMap) {
    this.sagaBaseMap = sagaBaseMap;
  }

  public Map<String, ServicePointState> getServiceStates() {
    return serviceStates;
  }

  public void setServiceStates(Map<String, ServicePointState> serviceStates) {
    this.serviceStates = serviceStates;
  }

  public int getOrder() {
    return order;
  }

  public void setOrder(int order) {
    this.order = order;
  }

  public Date getLastModifyDate() {
    return lastModifyDate;
  }

  public void setLastModifyDate(Date lastModifyDate) {
    this.lastModifyDate = lastModifyDate;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("SagaContext{");
    sb.append("sagaId=").append(sagaId).append('\n');
    sb.append(", name=").append(name).append('\n');
    sb.append(", lastModifyDate=").append(lastModifyDate).append('\n');
    sb.append(", sagaBaseMap=").append(sagaBaseMap).append('\n');
    sb.append(", serviceStates=").append(serviceStates);
    sb.append('}');
    return sb.toString();
  }
}
