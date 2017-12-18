package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class CompensatedStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.COMPENSATED;

  public CompensatedStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {
    throw new IllegalStateException("Compensated status can not change to Processing status");
  }

  @Override
  public void toCompleted() {
    throw new IllegalStateException("Compensated status can not change to Completed status");
  }

  @Override
  public void toPrepareCompensate() {
    throw new IllegalStateException("Compensated status can not change to PrepareCompensate status");
  }

  @Override
  public void toCompensating() {
    throw new IllegalStateException("Compensated status can not change to Compensating status");
  }

  @Override
  public void toCompensated() {
    // already be Compensated
  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
