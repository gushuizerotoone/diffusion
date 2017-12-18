package io.github.gushuizerotoone.diffuse.core.servicepoint;

import io.github.gushuizerotoone.diffuse.core.SagaContext;

import java.util.List;

public interface ServicePoint {

  String getName();

  boolean isLeaf();

  ServicePoint getNext();

  void setNext(ServicePoint servicePoint);

  SagaContext normalProcess();

  SagaContext compensate();

  void fillRedoStates(List<ServicePointRedoStatus> redoStates);

  ServicePointStatus getState();
}
