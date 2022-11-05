package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo NOT_FOUND.
 */
public class EntregaNotFoundException extends RuntimeException {

  public EntregaNotFoundException() {

    super("Entrega não encontrada.");
  }
}
