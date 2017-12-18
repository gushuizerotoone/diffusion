package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class CompletedStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.COMPLETED;

  public CompletedStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {
    throw new IllegalStateException("Completed status can not change to Processing status");
  }

  @Override
  public void toCompleted() {
    // already be Completed
  }

  @Override
  public void toPrepareCompensate() {
    servicePointState.setCurrentStatus(servicePointState.getPrepareCompensateStatus());
  }

  @Override
  public void toCompensating() {
    throw new IllegalStateException("Completed status can not change to Compensating status");
  }

  @Override
  public void toCompensated() {
    throw new IllegalStateException("Completed status can not change to Compensated status");
  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
