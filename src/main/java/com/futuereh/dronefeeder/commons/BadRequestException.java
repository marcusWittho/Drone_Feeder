package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo BAD_REQUEST.
 */
public class BadRequestException extends RuntimeException {

  public BadRequestException(String message) {

    super(message);
  }
}
