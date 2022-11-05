package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo CONFLICT.
 */
public class EntregaExistsException extends RuntimeException {

  public EntregaExistsException() {

    super("Entrega já está cadastrada.");
  }
}
