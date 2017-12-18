package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class ProcessingStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.PROCESSING;

  public ProcessingStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {
    // already be Processing
  }

  @Override
  public void toCompleted() {
    servicePointState.setCurrentStatus(servicePointState.getCompletedStatus());
  }

  @Override
  public void toPrepareCompensate() {
    servicePointState.setCurrentStatus(servicePointState.getPrepareCompensateStatus());
  }

  @Override
  public void toCompensating() {
    throw new IllegalStateException("Processing status can not change to Compensating status");
  }

  @Override
  public void toCompensated() {
    throw new IllegalStateException("Processing status can not change to Compensated status");
  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
