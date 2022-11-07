package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo NOT_FOUND.
 */
public class NotFoundException extends RuntimeException {

  public NotFoundException(String message) {

    super(message);
  }
}
