package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo BAD_REQUEST.
 */
public class CustomBadRequestException extends RuntimeException {

  public CustomBadRequestException(String message) {

    super(message);
  }
}
