package io.github.gushuizerotoone.diffuse.core;

import java.util.Optional;

public interface SagaDefinitionService {
  void register(SagaDefinition<?> definition);
  Optional<SagaDefinition> getSagaDefinition(String defName);
}
