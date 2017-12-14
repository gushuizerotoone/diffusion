package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public interface ServicePoint {

  String getName();

  boolean isLeaf();

  ServicePoint getNext();

  void setNext(ServicePoint servicePoint);

  SagaContext normalProcess();

  SagaContext compensate();

  void fillRedoStates(List<ServicePointRedoState> redoStates);

  ServicePointState getState();
}
