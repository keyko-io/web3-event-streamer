package io.keyko.monitoring.exceptions;

public class TypeConversionException extends EventStreamerException {

  public TypeConversionException(String message, Throwable e) {
    super(message, e);
  }

  public TypeConversionException(String message) {
    super(message);
  }
}
