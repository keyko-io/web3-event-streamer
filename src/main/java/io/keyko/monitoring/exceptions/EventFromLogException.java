package io.keyko.monitoring.exceptions;

public class EventFromLogException extends EventStreamerException {
  public EventFromLogException(String message, Throwable e) {
    super(message, e);
  }

  public EventFromLogException(String message) {
    super(message);
  }
}
