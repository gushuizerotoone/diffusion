package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointState;
import io.github.gushuizerotoone.diffuse.core.servicepoint.ServicePointStatus;

import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class SagaContext {
  private String sagaId;
  private String name;
  private Map<String, Object> sagaBaseMap;
  private Map<String, ServicePointState> serviceStates;
  private int order = 0;
  private Date lastModifyDate;
  private SagaStatus sagaStatus;

  private static final String REDO_POLICY = "_Redo_Policy";

  public SagaContext(String sagaId, String name) {
    this.sagaId = sagaId;
    this.name = name;
    sagaBaseMap = new ConcurrentHashMap<>(8);
    serviceStates = new ConcurrentHashMap<>(8);
    lastModifyDate = new Date();
  }

  public void appendService(Class<? extends ServiceAdaptor> serviceAdaptorClass) {
    ServicePointState state = new ServicePointState(serviceAdaptorClass);
    state.setOrder(++order);

    serviceStates.put(serviceAdaptorClass.getSimpleName(), state);
  }

  public void fillBase(Map<String, Object> elements) {
    sagaBaseMap.putAll(elements);
  }

  public void fillBase(String key, String value) {
    sagaBaseMap.put(key, value);
  }

  public void fillRedoPolicy(final RedoPolicy redoPolicy) {
    sagaBaseMap.put(REDO_POLICY, redoPolicy.getClass().getName());
  }

  public String getRedoPolicyClassName() {
    return (String)sagaBaseMap.get(REDO_POLICY);
  }

  public void setServiceState(String serviceName, ServicePointState state) {
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

  public SagaStatus normalizeSagaStatus() {
    Map<String, ServicePointState> serviceStates = getServiceStates();

    Map<ServicePointStatus, Integer> serviceStatusCount = serviceStates.values()
            .stream()
            .collect(Collectors.toMap(s -> s.getCurrentStatus(), s -> 1, (v1, v2) -> v1 + v2));

    if (serviceStatusCount.getOrDefault(ServicePointStatus.COMPENSATING, 0) > 0 || serviceStatusCount.getOrDefault(ServicePointStatus.PREPARE_COMPENSATE, 0) > 0) {
      return setAndGetSagaStatus(SagaStatus.COMPENSATING);
    }

    if (serviceStatusCount.getOrDefault(ServicePointStatus.COMPLETED, 0) == serviceStates.size()) {
      return setAndGetSagaStatus(SagaStatus.COMPLETED);
    }

    if (serviceStatusCount.getOrDefault(ServicePointStatus.COMPENSATED, 0) == serviceStates.size()) {
      return setAndGetSagaStatus(SagaStatus.COMPENSATED);
    }

    return setAndGetSagaStatus(SagaStatus.PROCESSING);
  }

  private SagaStatus setAndGetSagaStatus(SagaStatus sagaStatus) {
    setSagaStatus(sagaStatus);
    return sagaStatus;
  }

  public void setSagaStatus(SagaStatus sagaStatus) {
    this.sagaStatus = sagaStatus;
  }

  public boolean isSagaNotEnded() {
    return sagaStatus != SagaStatus.COMPLETED && sagaStatus != SagaStatus.COMPENSATED;
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

  public SagaStatus getSagaStatus() {
    return sagaStatus;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("SagaContext{");
    sb.append("sagaId=").append(sagaId).append('\n');
    sb.append(", name=").append(name).append('\n');
    sb.append(", sagaStatus=").append(sagaStatus).append('\n');
    sb.append(", lastModifyDate=").append(lastModifyDate).append('\n');
    sb.append(", sagaBaseMap=").append(sagaBaseMap).append('\n');
    sb.append(", serviceStates=").append(serviceStates);
    sb.append('}');
    return sb.toString();
  }
}
