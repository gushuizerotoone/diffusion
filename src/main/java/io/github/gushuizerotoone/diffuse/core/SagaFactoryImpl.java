package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SagaFactoryImpl implements SagaFactory {

  private Map<Class, Object> instanceMap = new ConcurrentHashMap<>();

  @Override
  public ServiceAdaptor getServiceAdaptor(Class<? extends ServiceAdaptor> clazz) {
    return getInstance(clazz);
  }

  @Override
  public ServiceAdaptor getServiceAdaptor(String className) {
    return getServiceAdaptor(forName(className));
  }

  @Override
  public RedoPolicy getRedoPolicy(Class<? extends RedoPolicy> clazz) {
    return getInstance(clazz);
  }

  @Override
  public RedoPolicy getRedoPolicy(String className) {
    return getRedoPolicy(forName(className));
  }

  private <T> Class<T> forName(String className) {
    Class<T> clazz = null;
    try {
      clazz = (Class<T>)Class.forName(className);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    return clazz;
  }

  private <T> T getInstance(Class<T> clazz) {
    try {
      return (T) instanceMap.getOrDefault(clazz, clazz.newInstance());
    } catch (InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Catch exception while get instance for class " + clazz.getSimpleName());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new RuntimeException("Catch exception while get instance for class " + clazz.getSimpleName());
    }
  }
}
