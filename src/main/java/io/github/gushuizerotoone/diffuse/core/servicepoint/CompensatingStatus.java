package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class CompensatingStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.COMPENSATING;

  public CompensatingStatus(ServicePointState servicePointState) {
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
