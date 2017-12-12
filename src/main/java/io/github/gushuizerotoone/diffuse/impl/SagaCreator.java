package io.github.gushuizerotoone.diffuse.impl;

import io.github.gushuizerotoone.diffuse.core.Saga;
import io.github.gushuizerotoone.diffuse.core.SagaContext;
import io.github.gushuizerotoone.diffuse.core.SagaDefinition;
import io.github.gushuizerotoone.diffuse.core.SagaDefinitionService;
import io.github.gushuizerotoone.diffuse.core.SagaStatus;
import io.github.gushuizerotoone.diffuse.spi.SagaRepo;

import java.util.Optional;

public class SagaCreator {

  private SagaDefinitionService definitionService;
  private SagaRepo sagaRepo;

  public <T> Saga createSaga(String defName, SagaContext sagaContext, String outerId) {
    Optional<SagaDefinition> optSagaDef = definitionService.getSagaDefinition(defName);
    SagaDefinition def = optSagaDef.orElseThrow(IllegalStateException::new);
    SagaImpl saga = new SagaImpl();
    saga.setDefName(def.getName());
    saga.setContext(sagaContext);
    saga.setOuterId(outerId);
    saga.setStatus(SagaStatus.CREATED);
    sagaRepo.saveSaga(saga);
    return saga;
  }

}
