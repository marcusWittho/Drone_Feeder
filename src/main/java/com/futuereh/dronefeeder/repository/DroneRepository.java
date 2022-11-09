package com.futuereh.dronefeeder.repository;

import com.futuereh.dronefeeder.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Interface responsável por ceder os métodos necessários para manipulação do DB.
 */
@Repository
public interface DroneRepository extends JpaRepository<Drone, Integer> {

  boolean existsBySerialNumber(String serialNumber);

  @Query("select d from Drone d where d.operando = false")
  List<Drone> findIsOperandoFalse();
}
