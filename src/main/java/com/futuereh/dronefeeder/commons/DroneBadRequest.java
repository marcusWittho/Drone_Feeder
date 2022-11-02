package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo BAD_REQUEST.
 */
public class DroneBadRequest extends RuntimeException {

  public DroneBadRequest(String message) {

    super(message);
  }
}
