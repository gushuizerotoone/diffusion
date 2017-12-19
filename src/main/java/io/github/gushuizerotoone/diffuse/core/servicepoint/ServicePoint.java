package io.github.gushuizerotoone.diffuse.core.servicepoint;

import io.github.gushuizerotoone.diffuse.core.SagaContext;

import java.util.List;

public interface ServicePoint {

  String getName();

  boolean isLeaf();

  void setNext(ServicePoint servicePoint);

  SagaContext normalProcess();

  SagaContext compensate();

}
