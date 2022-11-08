package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo INTERNAL_SERVER_ERROR. */
public class UnexpectedErrorException extends RuntimeException {

  public UnexpectedErrorException() {

    super("Erro inesperado.");
  }
}
