package io.github.gushuizerotoone.diffuse.core.servicepoint;

import java.time.Instant;

public class NextAction {
  private ActionType actionType;
  private Integer countsLeft = 3; // default retry times
  private Instant actionTime = Instant.now(); // default happen time
  private Long periodInSeconds = 30l; // default period

  public NextAction(ActionType actionType) {
    this.actionType = actionType;
  }

  public ActionType getActionType() {
    return actionType;
  }

  public void setActionType(ActionType actionType) {
    this.actionType = actionType;
  }

  public Integer getCountsLeft() {
    return countsLeft;
  }

  public void setCountsLeft(Integer countsLeft) {
    this.countsLeft = countsLeft;
  }

  public Instant getActionTime() {
    return actionTime;
  }

  public void setActionTime(Instant actionTime) {
    this.actionTime = actionTime;
  }

  public Long getPeriodInSeconds() {
    return periodInSeconds;
  }

  public void setPeriodInSeconds(Long periodInSeconds) {
    this.periodInSeconds = periodInSeconds;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("{");
    sb.append("actionType=").append(actionType);
    sb.append(", countsLeft=").append(countsLeft);
    sb.append(", actionTime=").append(actionTime);
    sb.append(", periodInSeconds=").append(periodInSeconds);
    sb.append('}');
    return sb.toString();
  }
}
