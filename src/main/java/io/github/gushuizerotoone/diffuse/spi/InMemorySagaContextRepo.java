package io.github.gushuizerotoone.diffuse.spi;

import io.github.gushuizerotoone.diffuse.core.SagaContext;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class InMemorySagaContextRepo implements SagaContextRepo {
  private Map<String, SagaContext> sagaContextMap = new ConcurrentHashMap<>();

  @Override
  public void saveSagaContext(SagaContext sagaContext) {
    sagaContextMap.put(sagaContext.getSagaId(), sagaContext);
  }

  @Override
  public SagaContext getSagaContext(String sagaId) {
    return sagaContextMap.get(sagaId);
  }

  @Override
  public List<SagaContext> getTimeoutSagaContext(long timeoutSeconds) {
    return sagaContextMap.values()
            .stream()
            .filter(sagaContext -> (System.currentTimeMillis() - sagaContext.getLastModifyDate().getTime()) > timeoutSeconds * 1000)
            .sorted(Comparator.comparing(SagaContext::getLastModifyDate))
            .collect(Collectors.toList());
  }
}
