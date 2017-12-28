package io.github.gushuizerotoone.diffuse.core.servicepoint;

import java.time.Instant;

public class NextAction {
  private ActionType actionType;
  private Integer countsLeft;
  private Instant instant;
  private Long periodInSeconds;

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

  public Instant getInstant() {
    return instant;
  }

  public void setInstant(Instant instant) {
    this.instant = instant;
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
    sb.append(", instant=").append(instant);
    sb.append(", periodInSeconds=").append(periodInSeconds);
    sb.append('}');
    return sb.toString();
  }
}
