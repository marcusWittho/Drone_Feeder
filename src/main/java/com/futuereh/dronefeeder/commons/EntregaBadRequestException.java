package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo BAD_REQUEST.
 */
public class EntregaBadRequestException extends RuntimeException {

  public EntregaBadRequestException(String message) {

    super(message);
  }
}
