package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo NOT_FOUND.
 */
public class DroneNotFoundException extends RuntimeException {

  public DroneNotFoundException() {

    super("Drone não encontrado.");
  }
}
