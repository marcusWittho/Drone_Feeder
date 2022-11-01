package com.futuereh.dronefeeder.dto;

import com.sun.istack.NotNull;

/**
 * Método DroneSto.
 */
public class DroneDto {

  private String serialNumber;
  private double latitude;
  private double longitude;
  private boolean operando;

  public String getSerialNumber() {
    return serialNumber;
  }

  @NotNull
  public void setSerialNumber(String serialNumber) {
    this.serialNumber = serialNumber;
  }

  public double getLatitude() {
    return latitude;
  }

  public void setLatitude(double latitude) {
    this.latitude = latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public void setLongitude(double longitude) {
    this.longitude = longitude;
  }

  public boolean isOperando() {
    return operando;
  }

  public void setOperando(boolean operando) {
    this.operando = operando;
  }
}
