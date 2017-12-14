package io.github.gushuizerotoone.diffuse.core;

import java.util.ArrayList;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class SagaContext {
  private String sagaId;
  private String name;
  private Map<String, Object> sagaBaseMap;
  private Map<String, Map<String, Object>> contextMap;
  private List<String> serviceOrders;

  private static final String SAGA_BASE = "_Saga_Base";
  private static final String REDO_POLICY = "_Redo_Policy";

  public SagaContext(String sagaId, String name) {
    this.sagaId = sagaId;
    this.name = name;
    sagaBaseMap = new ConcurrentHashMap<>();
    contextMap = new ConcurrentHashMap<>();
    serviceOrders = new ArrayList<>(4);
  }

  public void appendServiceName(String name) {
    serviceOrders.add(name);
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

  public void fill(String serviceName, ServiceResponse serviceResponse) {
    if (contextMap.get(serviceName) == null) {
      contextMap.put(serviceName, new ConcurrentHashMap<>());
    }

    contextMap.get(serviceName).putAll(serviceResponse.getResponse());
  }

  public Optional<String> getServiceResponseValue(String serviceName, String key) {
    Object value = contextMap.get(serviceName).get(key);
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

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("SagaContext{");
    sb.append("sagaId=").append(sagaId).append('\n');
    sb.append(", name=").append(name).append('\n');
    sb.append(", sagaBaseMap=").append(sagaBaseMap).append('\n');
    sb.append(", serviceOrders=").append(serviceOrders).append('\n');
    sb.append(", contextMap=").append(contextMap);
    sb.append('}');
    return sb.toString();
  }
}
