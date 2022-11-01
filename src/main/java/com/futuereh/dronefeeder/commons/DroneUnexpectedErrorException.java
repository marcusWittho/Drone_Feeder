package com.futuereh.dronefeeder.commons;

/**
 * Método chamado quando ocorrer algum erro que não foi tratado.
 */
public class DroneUnexpectedErrorException extends RuntimeException {

  public DroneUnexpectedErrorException() {

    super("Erro inesperado.");
  }
}
