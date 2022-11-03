package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo BAD_REQUEST.
 */
public class DroneBadRequestException extends RuntimeException {

  public DroneBadRequestException(String message) {

    super(message);
  }
}
