package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class CompletedStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.COMPLETED;

  public CompletedStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {

  }

  @Override
  public void toCompleted() {

  }

  @Override
  public void toPrepareCompensate() {
  }

  @Override
  public void toCompensating() {

  }

  @Override
  public void toCompensated() {

  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
