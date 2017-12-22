package io.github.gushuizerotoone.diffuse.core.servicepoint;

public enum ServicePointAction {
  TO_RETRY,
  TO_COMPENSATE,
  DEPEND_ON_POLICY,
  ;

  private int secondsLater;

  ServicePointAction() {
    this(0); // default
  }

  ServicePointAction(int secondsLater) {
    this.secondsLater = secondsLater;
  }

  public int getSecondsLater() {
    return secondsLater;
  }

  public void setSecondsLater(int secondsLater) {
    this.secondsLater = secondsLater;
  }
}
