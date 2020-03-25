package io.keyko.monitoring.exceptions;

public class EtherscanException extends EventStreamerException {

  public EtherscanException(String message, Throwable e) {
    super(message, e);
  }

  public EtherscanException(String message) {
    super(message);
  }
}
