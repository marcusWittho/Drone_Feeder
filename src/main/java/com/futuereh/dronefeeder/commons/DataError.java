package com.futuereh.dronefeeder.commons;

/**
 * Classe base para criação das mensagens de erro.
 */
public class DataError {

  private String error;

  public DataError() {}

  public DataError(String error) {
    this.error = error;
  }

  public String getError() {
    return error;
  }

  public void setError(String error) {
    this.error = error;
  }
}
