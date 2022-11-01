package com.futuereh.dronefeeder.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela de drones.
 */
@Entity
@Table(name = "tb_drone")
public class Drone {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String serialNumber;
  private double latitude;
  private double longitude;
  private boolean operando;

  /**
   * Construtor da classe Drone.
   *
   * <p>@param latitude - recebe a latitude atual do drone.</p>
   * <p>@param longitude - recebe a longitude atual do drone.</p>
   * <p>@param operando - recebe true caso o drone esteja realizando alguma entrega
   *     e falso se estiver ocioso.</p>
   */
  public Drone(String serialNumber, double latitude, double longitude, boolean operando) {
    this.serialNumber = serialNumber;
    this.latitude = latitude;
    this.longitude = longitude;
    this.operando = operando;
  }

  public Drone() {}

  public Integer getId() {
    return id;
  }

  public String getSerialNumber() {
    return serialNumber;
  }

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

  @Override
  public String toString() {
    return "Drone { "
      + "id: " + id
      + ", latitude: " + latitude
      + ", longitude: " + longitude
      + ", operando: " + operando
      + " }";
  }
}