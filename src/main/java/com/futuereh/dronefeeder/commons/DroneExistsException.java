package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo CONFLICT.
 */
public class DroneExistsException extends RuntimeException {

  public DroneExistsException() {

    super("Drone já cadastrado.");
  }
}
