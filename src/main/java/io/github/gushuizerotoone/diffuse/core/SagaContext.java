package io.github.gushuizerotoone.diffuse.core;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class SagaContext {
  private String sagaId;
  private String name;
  private Map<String, Object> sagaBaseMap;
  private Map<String, ServicePointState> serviceStates;
  private int index = 0;

  private static final String REDO_POLICY = "_Redo_Policy";

  public SagaContext(String sagaId, String name) {
    this.sagaId = sagaId;
    this.name = name;
    sagaBaseMap = new ConcurrentHashMap<>();
    serviceStates = new ConcurrentHashMap<>(4);
  }

  public void appendServiceName(String name) {
    ServicePointState state = new ServicePointState(ServicePointStatus.INIT, name);
    state.setIndex(++index);

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

    serviceStates.put(serviceName, old);
  }

  public Optional<String> getServiceResponseValue(String serviceName, String key) {
    Object value = serviceStates.get(serviceName).getContent().get(key);
    return Optional.ofNullable(value.toString());
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

  public int getIndex() {
    return index;
  }

  public void setIndex(int index) {
    this.index = index;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("SagaContext{");
    sb.append("sagaId=").append(sagaId).append('\n');
    sb.append(", name=").append(name).append('\n');
    sb.append(", sagaBaseMap=").append(sagaBaseMap).append('\n');
    sb.append(", serviceStates=").append(serviceStates);
    sb.append('}');
    return sb.toString();
  }
}
