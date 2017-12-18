package io.github.gushuizerotoone.diffuse.core.servicepoint;

public interface ServicePointStatusHolder {

  void toProcessing();

  void toCompleted();

  void toPrepareCompensate();

  void toCompensating();

  void toCompensated();

  ServicePointStatus getStatus();

}
