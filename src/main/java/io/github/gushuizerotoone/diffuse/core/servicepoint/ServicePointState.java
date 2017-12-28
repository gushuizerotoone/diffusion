package io.github.gushuizerotoone.diffuse.core.servicepoint;

import io.github.gushuizerotoone.diffuse.core.ServiceAdaptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ServicePointState {
  private String className;
  private Integer order;
  private Date date;
  private Map<String, Object> content;

  private NextAction nextAction;
  private ServicePointStatus currentStatus;

  public ServicePointState(Class<? extends ServiceAdaptor> serviceAdaptorClass) {
    this.className = serviceAdaptorClass.getName();
    this.date = new Date();
    this.content = new HashMap<>();
    this.currentStatus = ServicePointStatus.PREPARE_PROCESS;
    this.nextAction = new NextAction(ActionType.ROUTINE);
  }

  public void fillData(ServicePointStatus currentStatus, NextAction nextAction, Map<String, Object> resultParams) {
    this.currentStatus = currentStatus;
    this.nextAction = nextAction;
    this.content.putAll(resultParams);
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public Integer getOrder() {
    return order;
  }

  public void setOrder(Integer order) {
    this.order = order;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Map<String, Object> getContent() {
    return content;
  }

  public void setContent(Map<String, Object> content) {
    this.content = content;
  }

  public NextAction getNextAction() {
    return nextAction;
  }

  public void setNextAction(NextAction nextAction) {
    this.nextAction = nextAction;
  }

  public ServicePointStatus getCurrentStatus() {
    return currentStatus;
  }

  public void setCurrentStatus(ServicePointStatus currentStatus) {
    this.currentStatus = currentStatus;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("className='").append(className).append('\'');
    sb.append(", currentStatus=").append(currentStatus);
    sb.append(", order=").append(order);
    sb.append(", date=").append(date);
    sb.append(", content=").append(content);
    sb.append(", nextAction=").append(nextAction);
    sb.append('}');
    return sb.toString();
  }
}
