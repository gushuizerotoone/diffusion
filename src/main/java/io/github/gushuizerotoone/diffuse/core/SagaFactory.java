package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaScheduler;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

public interface SagaFactory {

  ServiceAdaptor getServiceAdaptor(Class<? extends ServiceAdaptor> clazz);
  ServiceAdaptor getServiceAdaptor(String className);

  RedoPolicy getRedoPolicy(Class<? extends RedoPolicy> clazz);
  RedoPolicy getRedoPolicy(String className);

  SagaContextRepo getSagaContextRepo(Class<? extends SagaContextRepo> clazz);
  SagaContextRepo getSagaContextRepo(String className);

  SagaScheduler getSagaScheduler(Class<? extends SagaScheduler> clazz);
  SagaScheduler getSagaScheduler(String className);

}
