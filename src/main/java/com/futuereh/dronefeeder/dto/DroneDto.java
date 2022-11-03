package com.futuereh.dronefeeder.dto;

import com.futuereh.dronefeeder.model.Entrega;
import com.sun.istack.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Método DroneSto.
 */
public class DroneDto {

  private String serialNumber;
  private double latitude;
  private double longitude;
  private boolean operando;

  private List<Entrega> entregas = new ArrayList<>();

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

  public List<Entrega> getEntregas() {
    return entregas;
  }

  public void setEntregas(List<Entrega> entregas) {
    this.entregas = entregas;
  }
}
