package io.github.gushuizerotoone.diffuse.core;

import io.github.gushuizerotoone.diffuse.core.policy.RedoPolicy;
import io.github.gushuizerotoone.diffuse.core.schedule.SagaScheduler;
import io.github.gushuizerotoone.diffuse.spi.SagaContextRepo;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SagaFactoryImpl implements SagaFactory {

  private Map<Class, Object> instanceMap = new ConcurrentHashMap<>();

  private static SagaFactoryImpl instance;

  public static SagaFactoryImpl getInstance() {
    if (instance == null) {
      synchronized (SagaFactoryImpl.class) {
        if (instance == null) {
          instance = new SagaFactoryImpl();
        }
      }
    }
    return instance;
  }

  private SagaFactoryImpl() {
  }

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

  @Override
  public SagaContextRepo getSagaContextRepo(Class<? extends SagaContextRepo> clazz) {
    return getInstance(clazz);
  }

  @Override
  public SagaContextRepo getSagaContextRepo(String className) {
    return getSagaContextRepo(forName(className));
  }

  @Override
  public SagaScheduler getSagaScheduler(Class<? extends SagaScheduler> clazz) {
    return getInstance(clazz);
  }

  @Override
  public SagaScheduler getSagaScheduler(String className) {
    return getSagaScheduler(forName(className));
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
      T classInstance = (T)instanceMap.getOrDefault(clazz, clazz.newInstance());
      instanceMap.putIfAbsent(clazz, classInstance);
      return classInstance;
    } catch (InstantiationException e) {
      e.printStackTrace();
      throw new RuntimeException("Catch exception while get instance for class " + clazz.getSimpleName());
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      throw new RuntimeException("Catch exception while get instance for class " + clazz.getSimpleName());
    }
  }
}
