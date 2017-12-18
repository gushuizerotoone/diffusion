package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class CompensatingStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.COMPENSATING;

  public CompensatingStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {
    throw new IllegalStateException("Compensating status can not change to Processing status");
  }

  @Override
  public void toCompleted() {
    throw new IllegalStateException("Compensating status can not change to Completed status");
  }

  @Override
  public void toPrepareCompensate() {
    throw new IllegalStateException("Compensating status can not change to PrepareCompensate status");
  }

  @Override
  public void toCompensating() {
    // already be Compensating
  }

  @Override
  public void toCompensated() {
    servicePointState.setCurrentStatus(servicePointState.getCompensatedStatus());
  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
