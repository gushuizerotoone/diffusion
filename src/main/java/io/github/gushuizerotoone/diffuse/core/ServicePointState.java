package io.github.gushuizerotoone.diffuse.core;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServicePointState {
  private String name;
  private ServicePointStatus status;
  private Integer order;
  private Date date;
  private Map<String, Object> content;

  public ServicePointState(ServicePointStatus status, String name, Map<String, Object> content) {
    this.status = status;
    this.date = new Date();
    this.name = name;
    this.content = content;
  }

  public ServicePointState(ServicePointStatus status, String name) {
    this.status = status;
    this.name = name;
    this.date = new Date();
    this.content = new HashMap<>();
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public Map<String, Object> getContent() {
    return content;
  }

  public void setContent(Map<String, Object> content) {
    this.content = content;
  }

  public ServicePointStatus getStatus() {
    return status;
  }

  public void setStatus(ServicePointStatus status) {
    this.status = status;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("{");
    sb.append("name='").append(name).append('\'');
    sb.append(", status=").append(status);
    sb.append(", order=").append(order);
//    sb.append(", date=").append(date); // TODO
    sb.append(", content=").append(content);
    sb.append('}');
    return sb.toString();
  }
}
