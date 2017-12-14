package io.github.gushuizerotoone.diffuse.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Saga implements Redoable<SagaContext> {

  private SagaContext sagaContext;

  private RedoPolicy redoPolicy;

  private ServicePoint firstServicePoint;

  private ServicePoint lastServicePoint;

  public Saga(SagaContext sagaContext) {
    this.sagaContext = sagaContext;
  }

  public Saga addService(ServiceAdaptor serviceAdaptor) {
    ServicePoint servicePoint = new CompositeServicePoint(sagaContext, serviceAdaptor);
    if (firstServicePoint == null) {
      firstServicePoint = servicePoint;
      lastServicePoint = servicePoint;
    }

    lastServicePoint.setNext(servicePoint);
    lastServicePoint = servicePoint;

    sagaContext.appendServiceName(serviceAdaptor.getName());
    return this;
  }

  public Saga redoPolicy(RedoPolicy redoPolicy) {
    this.redoPolicy = redoPolicy;
    sagaContext.fillRedoPolicy(redoPolicy);
    return this;
  }

  public Saga process() {
    firstServicePoint.normalProcess();
    return this;
  }

  public SagaContext getSagaContext() {
    return sagaContext;
  }

  @Override
  public SagaContext redo() {
    Objects.requireNonNull(redoPolicy, "can not find redoPolicy: " + toShortString());

    List<ServicePointRedoState> redoStates = new ArrayList<>();
    firstServicePoint.fillRedoStates(redoStates);

    Strategy strategy = redoPolicy.getStrategy(redoStates);
    if (redoPolicy.getStrategy(redoStates) == null) {
      throw new RuntimeException("can not redo saga: " + toShortString());
    }

    strategy.forward(firstServicePoint);
    return sagaContext;
  }

  public String toShortString() {
    return String.format("[%s - %s]", sagaContext.getName(), sagaContext.getSagaId());
  }

}
