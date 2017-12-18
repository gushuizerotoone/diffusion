package io.github.gushuizerotoone.diffuse.core.servicepoint;

public class PrepareProcessStatus implements ServicePointStatusHolder {

  private ServicePointState servicePointState;
  private final static ServicePointStatus status = ServicePointStatus.PREPARE_PROCESS;

  public PrepareProcessStatus(ServicePointState servicePointState) {
    this.servicePointState = servicePointState;
  }

  @Override
  public void toProcessing() {
    servicePointState.setCurrentStatus(servicePointState.getProcessingStatus());
  }

  @Override
  public void toCompleted() {
    throw new IllegalStateException("PrepareProcessing status can not change to Completed status");
  }

  @Override
  public void toPrepareCompensate() {
    servicePointState.setCurrentStatus(servicePointState.getPrepareCompensateStatus());
  }

  @Override
  public void toCompensating() {
    throw new IllegalStateException("PrepareProcessing status can not change to Compensating status");
  }

  @Override
  public void toCompensated() {
    throw new IllegalStateException("PrepareProcessing status can not change to Compensated status");
  }

  @Override
  public ServicePointStatus getStatus() {
    return status;
  }
}
