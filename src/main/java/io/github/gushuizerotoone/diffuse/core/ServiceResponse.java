package io.github.gushuizerotoone.diffuse.core;

import java.util.Map;

public class ServiceResponse {
  private Map<String, Object> response;

  public ServiceResponse(Map<String, Object> response) {
    this.response = response;
  }

  public Map<String, Object> getResponse() {
    return response;
  }

  public void setResponse(Map<String, Object> response) {
    this.response = response;
  }
}
