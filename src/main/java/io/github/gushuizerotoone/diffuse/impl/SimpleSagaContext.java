package io.github.gushuizerotoone.diffuse.impl;

import io.github.gushuizerotoone.diffuse.core.SagaContext;

import java.util.Map;

public class SimpleSagaContext implements SagaContext {

  private Map<String, Object> valueMap;

  public Map<String, Object> getValueMap() {
    return valueMap;
  }
}
