package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.DroneExistsException;
import com.futuereh.dronefeeder.dto.DroneDto;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;
import org.springframework.stereotype.Service;

/**
 * Classe responsável pelas validações que antecedem as operações com o DB.
 */
@Service
public class DroneService {

  private DroneRepository repository;

  public DroneService(DroneRepository repository) {

    this.repository = repository;
  }

  /**
   * Método responsável por adicionar um novo drone ao DB.
   *
   * <p>@param drone - recebe o drone que será adicionado ao DB.</p>
   * <p>@return - retorna uma string com as informações do drone adicionado.</p>
   */
  public Drone addDrone(DroneDto droneDto) {

    if (repository.existsBySerialNumber(droneDto.getSerialNumber())) {
      throw new DroneExistsException();
    }

    Drone newDrone = new Drone(droneDto.getSerialNumber(), droneDto.getLatitude(),
        droneDto.getLongitude(), droneDto.isOperando());

    this.repository.save(newDrone);

    return newDrone;
  }
}