package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo INTERNAL_SERVER_ERROR. */
public class DroneUnexpectedErrorException extends RuntimeException {

  public DroneUnexpectedErrorException() {

    super("Erro inesperado.");
  }
}
