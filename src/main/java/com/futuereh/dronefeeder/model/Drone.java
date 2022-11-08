package com.futuereh.dronefeeder.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela de drones.
 */
@Entity
@Table(name = "tb_drone")
public class Drone {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String serialNumber;
  private double latitude;
  private double longitude;
  private boolean operando;

  @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Entrega> entregas;

  /**
   * Construtor da classe Drone.
   *
   * @param latitude - recebe a latitude atual do drone.
   * @param longitude - recebe a longitude atual do drone.
   * @param operando - recebe true caso o drone esteja realizando alguma entrega
   *     e falso se estiver ocioso.
   */
  public Drone(String serialNumber, double latitude, double longitude, boolean operando) {

    this.serialNumber = serialNumber;
    this.latitude = latitude;
    this.longitude = longitude;
    this.operando = operando;
    this.entregas = new ArrayList<>();
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

  public List<Entrega> getEntregas() {
    return entregas;
  }

  public void setEntregas(List<Entrega> entregas) {
    this.entregas = entregas;
  }

  public void addEntrega(Entrega entrega) {
    this.entregas.add(entrega);
  }
}
