package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo CONFLICT.
 */
public class CustomExistsException extends RuntimeException {

  public CustomExistsException(String message) {

    super(message);
  }
}
