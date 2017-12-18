package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;

public interface SagaFactory {

  ServiceAdaptor getServiceAdaptor(Class<? extends ServiceAdaptor> clazz);
  ServiceAdaptor getServiceAdaptor(String className);

  RedoPolicy getRedoPolicy(Class<? extends RedoPolicy> clazz);
  RedoPolicy getRedoPolicy(String className);

}
