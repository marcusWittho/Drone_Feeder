package com.futuereh.dronefeeder.commons;

/**
 * Método customizado para erros do tipo INTERNAL_SERVER_ERROR. */
public class CustomUnexpectedErrorException extends RuntimeException {

  public CustomUnexpectedErrorException() {

    super("Erro inesperado.");
  }
}
