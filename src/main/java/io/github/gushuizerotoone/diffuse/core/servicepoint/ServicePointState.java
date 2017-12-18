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

  private ServicePointStatusHolder prepareProcessStatus;
  private ServicePointStatusHolder processingStatus;
  private ServicePointStatusHolder completedStatus;
  private ServicePointStatusHolder prepareCompensateStatus;
  private ServicePointStatusHolder compensatingStatus;
  private ServicePointStatusHolder compensatedStatus;

  private ServicePointStatusHolder currentStatus;

  public ServicePointState(Class<? extends ServiceAdaptor> serviceAdaptorClass) {
    prepareProcessStatus = new PrepareProcessStatus(this);
    processingStatus = new ProcessingStatus(this);
    compensatedStatus = new CompletedStatus(this);
    prepareCompensateStatus = new PrepareCompensateStatus(this);
    compensatingStatus = new CompensatingStatus(this);
    compensatedStatus= new CompensatedStatus(this);
    completedStatus = new CompletedStatus(this);

    this.currentStatus = prepareProcessStatus; // init

    this.className = serviceAdaptorClass.getName();
    this.date = new Date();
    this.content = new HashMap<>();
  }

  public ServicePointStatus getStatus() {
    return currentStatus.getStatus();
  }

  public void fillContent(Map<String, Object> resultParams) {
    this.content.putAll(resultParams);
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

  public ServicePointStatusHolder getCurrentStatus() {
    return currentStatus;
  }

  public void setCurrentStatus(ServicePointStatusHolder currentStatus) {
    this.currentStatus = currentStatus;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public String getClassName() {
    return className;
  }

  public void setClassName(String className) {
    this.className = className;
  }

  public ServicePointStatusHolder getPrepareProcessStatus() {
    return prepareProcessStatus;
  }

  public void setPrepareProcessStatus(ServicePointStatusHolder prepareProcessStatus) {
    this.prepareProcessStatus = prepareProcessStatus;
  }

  public ServicePointStatusHolder getProcessingStatus() {
    return processingStatus;
  }

  public void setProcessingStatus(ServicePointStatusHolder processingStatus) {
    this.processingStatus = processingStatus;
  }

  public ServicePointStatusHolder getCompletedStatus() {
    return completedStatus;
  }

  public void setCompletedStatus(ServicePointStatusHolder completedStatus) {
    this.completedStatus = completedStatus;
  }

  public ServicePointStatusHolder getPrepareCompensateStatus() {
    return prepareCompensateStatus;
  }

  public void setPrepareCompensateStatus(ServicePointStatusHolder prepareCompensateStatus) {
    this.prepareCompensateStatus = prepareCompensateStatus;
  }

  public ServicePointStatusHolder getCompensatingStatus() {
    return compensatingStatus;
  }

  public void setCompensatingStatus(ServicePointStatusHolder compensatingStatus) {
    this.compensatingStatus = compensatingStatus;
  }

  public ServicePointStatusHolder getCompensatedStatus() {
    return compensatedStatus;
  }

  public void setCompensatedStatus(ServicePointStatusHolder compensatedStatus) {
    this.compensatedStatus = compensatedStatus;
  }

  @Override
  public String toString() {
    final StringBuffer sb = new StringBuffer("{");
    sb.append("className='").append(className).append('\'');
    sb.append(", currentStatus=").append(currentStatus.getStatus());
    sb.append(", order=").append(order);
//    sb.append(", date=").append(date); // TODO
    sb.append(", content=").append(content);
    sb.append('}');
    return sb.toString();
  }
}
