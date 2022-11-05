package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.DroneBadRequestException;
import com.futuereh.dronefeeder.commons.DroneExistsException;
import com.futuereh.dronefeeder.commons.DroneNotFoundException;
import com.futuereh.dronefeeder.commons.UnexpectedErrorException;
import com.futuereh.dronefeeder.dto.DroneDto;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    try {
      if (repository.existsBySerialNumber(droneDto.getSerialNumber())) {
        throw new DroneExistsException();
      }

      if (droneDto.getSerialNumber().isEmpty()) {
        throw new DroneBadRequestException("SerialNumber não foi informado.");
      }

      if (droneDto.getLatitude() == 0) {
        throw new DroneBadRequestException("Latitude não foi informada.");
      }

      if (droneDto.getLongitude() == 0) {
        throw new DroneBadRequestException("Longitude não foi informada.");
      }

      Drone newDrone = new Drone(droneDto.getSerialNumber(), droneDto.getLatitude(),
          droneDto.getLongitude(), droneDto.isOperando(), droneDto.getEntregas());

      this.repository.save(newDrone);

      return newDrone;
    } catch (DroneExistsException err) {
      throw err;
    } catch (DroneBadRequestException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }

  /**
   * Método responsável por obter um drone pelo id informado.
   *
   * @param id - recebe o id do drone a ser pesquisado.
   * @return - retorna um drone referente ao id informado no parâmetro.
   */
  public Drone droneById(Integer id) {

    try {
      Optional<Drone> drone = repository.findById(id);

      if (drone.isEmpty()) {
        throw new DroneNotFoundException();
      }

      return drone.get();
    } catch (DroneNotFoundException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }

  /**
   * Método responsável por obter os drones pelo status.
   *
   * @return - retorna uma lista de drones.
   */
  public List<Drone> droneByStatusFalse() {

    try {
      List<Drone> drones = repository.findAll();

      List<Drone> dronesLivres = drones.stream()
        .filter(drone -> !drone.isOperando())
        .collect(Collectors.toList());

      if (dronesLivres.isEmpty()) {
        throw new DroneNotFoundException();
      }

      return dronesLivres;
    } catch (DroneNotFoundException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }

  /**
   * Método responsável por atualizar determinado drone.
   *
   * @param id - recebe o id do drone que será atualizado.
   * @param droneDto - recebe as novas informações do drone.
   * @return - retorna o drone com as informaçẽos atualizadas.
   */
  public Drone updateDrone(Integer id, DroneDto droneDto) {

    try {

      Optional<Drone> toBeUpdated = repository.findById(id);

      if (toBeUpdated.isEmpty()) {
        throw new DroneNotFoundException();
      }

      if (droneDto.getSerialNumber().isEmpty()) {
        throw new DroneBadRequestException("SerialNumber não foi informado.");
      }

      if (droneDto.getLatitude() == 0) {
        throw new DroneBadRequestException("Latitude não foi informada.");
      }

      if (droneDto.getLongitude() == 0) {
        throw new DroneBadRequestException("Longitude não foi informada.");
      }

      toBeUpdated.get().setSerialNumber(droneDto.getSerialNumber());
      toBeUpdated.get().setLatitude(droneDto.getLatitude());
      toBeUpdated.get().setLongitude(droneDto.getLongitude());
      toBeUpdated.get().setOperando(droneDto.isOperando());

      repository.save(toBeUpdated.get());

      return toBeUpdated.get();
    } catch (DroneNotFoundException err) {
      throw err;
    } catch (DroneBadRequestException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }

  /**
   * Método responsável por remover determinado drone.
   *
   * @param id - recebe o id do drone que será removido.
   */
  public void removeDrone(Integer id) {

    try {
      Optional<Drone> toBeDeleted = repository.findById(id);

      if (toBeDeleted.isEmpty()) {
        throw new DroneNotFoundException();
      }

      repository.deleteById(id);
    } catch (DroneNotFoundException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }
}
