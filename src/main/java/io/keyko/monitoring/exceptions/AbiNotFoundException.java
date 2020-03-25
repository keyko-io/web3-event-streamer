package io.keyko.monitoring.exceptions;

public class AbiNotFoundException extends EventStreamerException {

  public AbiNotFoundException(String message, Throwable e) {
    super(message, e);
  }

  public AbiNotFoundException(String message) {
    super(message);
  }
}
