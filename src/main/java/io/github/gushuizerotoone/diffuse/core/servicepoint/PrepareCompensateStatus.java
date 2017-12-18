package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class PrepareCompensateStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.PREPARE_COMPENSATE;

  public PrepareCompensateStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {
    throw new IllegalStateException("PrepareCompensate status can not change to Processing status");
  }

  @Override
  public void toCompleted() {
    throw new IllegalStateException("PrepareCompensate status can not change to Completed status");
  }

  @Override
  public void toPrepareCompensate() {
    // already be PrepareCompensate
  }

  @Override
  public void toCompensating() {
    servicePointState.setCurrentStatus(servicePointState.getCompensatingStatus());
  }

  @Override
  public void toCompensated() {
    throw new IllegalStateException("PrepareCompensate status can not change to Compensated status");
  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
