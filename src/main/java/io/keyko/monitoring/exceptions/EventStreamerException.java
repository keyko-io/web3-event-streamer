package io.keyko.monitoring.exceptions;

public abstract class EventStreamerException extends Exception {

  public EventStreamerException(String message, Throwable e) {
    super(message, e);
  }

  public EventStreamerException(String message) {
    super(message);
  }
}
