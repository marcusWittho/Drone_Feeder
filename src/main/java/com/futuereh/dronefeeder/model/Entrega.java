package com.futuereh.dronefeeder.model;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidade que representa a tabela de entragas.
 */
@Entity
@Table(name = "tb_entrega")
public class Entrega {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Integer id;

  private String bairro;

  private String cep;

  private String rua;

  private Integer num;

  private String destinatario;

  private Date data;

  private boolean status;

  @ManyToOne
  @JoinColumn(name = "drone_id")
  private Drone drone;

  public Entrega() {}

  /**
   * Contrutor da classe Entrega. Recebe como parâmetros 7 informações referentes a entrage.
   */
  public Entrega(String bairro, String cep, String rua, Integer num,
                 String destinatario, Date data, boolean status, Drone drone) {

    this.bairro = bairro;
    this.cep = cep;
    this.rua = rua;
    this.num = num;
    this.destinatario = destinatario;
    this.data = data;
    this.status = status;
    this.drone = drone;
  }

  public Integer getId() {
    return id;
  }

  public String getBairro() {
    return bairro;
  }

  public void setBairro(String bairro) {
    this.bairro = bairro;
  }

  public String getCep() {
    return cep;
  }

  public void setCep(String cep) {
    this.cep = cep;
  }

  public String getRua() {
    return rua;
  }

  public void setRua(String rua) {
    this.rua = rua;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }

  public String getDestinatario() {
    return destinatario;
  }

  public void setDestinatario(String destinatario) {
    this.destinatario = destinatario;
  }

  public Date getData() {
    return data;
  }

  public void setData(Date data) {
    this.data = data;
  }

  public boolean isStatus() {
    return status;
  }

  public void setStatus(boolean status) {
    this.status = status;
  }

  public Drone getDrone() {
    return drone;
  }

  public void setDrone(Drone drone) {
    this.drone = drone;
  }
}
