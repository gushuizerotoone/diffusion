package io.github.gushuizerotoone.diffuse.core;

public class SagaContext {
  private String sagaId;
  private String name;
  private Integer servicePointLength;

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

  public Integer getServicePointLength() {
    return servicePointLength;
  }

  public void setServicePointLength(Integer servicePointLength) {
    this.servicePointLength = servicePointLength;
  }
}
