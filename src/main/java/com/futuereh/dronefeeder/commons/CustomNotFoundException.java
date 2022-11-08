package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo NOT_FOUND.
 */
public class CustomNotFoundException extends RuntimeException {

  public CustomNotFoundException(String message) {

    super(message);
  }
}
