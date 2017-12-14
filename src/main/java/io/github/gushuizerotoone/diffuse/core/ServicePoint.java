package io.github.gushuizerotoone.diffuse.core;

import java.util.List;

public interface ServicePoint {

  String getName();

  boolean isLeaf();

  Saga normalProcess(Saga saga);

  Saga compensate(Saga saga);

  void fillRedoStates(Saga saga, List<ServicePointRedoState> redoStates);
}
