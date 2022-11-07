package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo CONFLICT.
 */
public class ExistsException extends RuntimeException {

  public ExistsException(String message) {

    super(message);
  }
}
