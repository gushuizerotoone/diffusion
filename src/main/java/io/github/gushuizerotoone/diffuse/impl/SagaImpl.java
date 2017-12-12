package io.github.gushuizerotoone.diffuse.impl;

import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaStatus;
import io.github.gushuizerotoone.diffuse.core.SagaStepStatus;

import java.util.Date;

public class SagaImpl<CT extends SagaContext> implements Saga<CT> {

  private String id;

  private String defName;
  private String outerId;
  private SagaContext context;

  private SagaStatus status;
  private String step;
  private Date nextTimeout;
  private SagaStepStatus stepStatus;

  public String getDefName() {
    return defName;
  }

  public void setDefName(String defName) {
    this.defName = defName;
  }

  public SagaContext getContext() {
    return context;
  }

  public void setContext(SagaContext context) {
    this.context = context;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOuterId() {
    return outerId;
  }

  public void setOuterId(String outerId) {
    this.outerId = outerId;
  }

  public SagaStatus getStatus() {
    return status;
  }

  public void setStatus(SagaStatus status) {
    this.status = status;
  }

  public Date getNextTimeout() {
    return nextTimeout;
  }

  public void setNextTimeout(Date nextTimeout) {
    this.nextTimeout = nextTimeout;
  }

  public String getStep() {
    return step;
  }

  public void setStep(String step) {
    this.step = step;
  }

  @Override
  public void start() {

  }
}
