package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.CustomBadRequestException;
import com.futuereh.dronefeeder.commons.CustomExistsException;
import com.futuereh.dronefeeder.commons.CustomNotFoundException;
import com.futuereh.dronefeeder.commons.CustomUnexpectedErrorException;
import com.futuereh.dronefeeder.dto.DroneDto;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Classe responsável pelas validações que antecedem as operações com o DB.
 */
@Service
public class DroneService {

  private DroneRepository repository;

  private final Logger logger = Logger.getLogger(DroneService.class);

  public DroneService(DroneRepository repository) {

    this.repository = repository;
  }

  /**
   * Método responsável por adicionar um novo drone ao DB.
   *
   * <p>@param drone - recebe o drone que será adicionado ao DB.</p>
   * <p>@return - retorna uma string com as informações do drone adicionado.</p>
   */
  @Transactional
  public Drone addDrone(DroneDto droneDto) {

    try {
      if (repository.existsBySerialNumber(droneDto.getSerialNumber())) {
        throw new CustomExistsException("Drone já cadastrado.");
      }

      if (droneDto.getSerialNumber().isEmpty()) {
        throw new CustomBadRequestException("SerialNumber não foi informado.");
      }

      if (droneDto.getLatitude() == 0) {
        throw new CustomBadRequestException("Latitude não foi informada.");
      }

      if (droneDto.getLongitude() == 0) {
        throw new CustomBadRequestException("Longitude não foi informada.");
      }

      Drone newDrone = new Drone(droneDto.getSerialNumber(), droneDto.getLatitude(),
          droneDto.getLongitude(), droneDto.isOperando());

      this.repository.save(newDrone);

      return newDrone;
    } catch (CustomExistsException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (CustomBadRequestException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por retornar uma lista com os drones cadastrados.
   */
  public List<Drone> drones() {

    try {
      List<Drone> dronesList = repository.findAll();

      return dronesList;
    } catch (Exception err) {
      logger.error(err.getCause());
      throw err;
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
        throw new CustomNotFoundException("Drone não encontrado.");
      }

      return drone.get();
    } catch (CustomNotFoundException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por obter os drones pelo status.
   *
   * @return - retorna uma lista de drones.
   */
  public List<Drone> droneByStatusFalse() {

    try {
      List<Drone> dronesLivres = repository.findIsOperandoFalse();

      if (dronesLivres.isEmpty()) {
        throw new CustomNotFoundException("Drone não encontrado.");
      }

      return dronesLivres;
    } catch (CustomNotFoundException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por atualizar determinado drone.
   *
   * @param id - recebe o id do drone que será atualizado.
   * @param droneDto - recebe as novas informações do drone.
   * @return - retorna o drone com as informaçẽos atualizadas.
   */
  @Transactional
  public Drone updateDrone(Integer id, DroneDto droneDto) {

    try {

      Optional<Drone> toBeUpdated = repository.findById(id);

      if (toBeUpdated.isEmpty()) {
        throw new CustomNotFoundException("Drone não encontrado.");
      }

      if (droneDto.getSerialNumber().isEmpty()) {
        throw new CustomBadRequestException("SerialNumber não foi informado.");
      }

      if (droneDto.getLatitude() == 0) {
        throw new CustomBadRequestException("Latitude não foi informada.");
      }

      if (droneDto.getLongitude() == 0) {
        throw new CustomBadRequestException("Longitude não foi informada.");
      }

      toBeUpdated.get().setSerialNumber(droneDto.getSerialNumber());
      toBeUpdated.get().setLatitude(droneDto.getLatitude());
      toBeUpdated.get().setLongitude(droneDto.getLongitude());
      toBeUpdated.get().setOperando(droneDto.isOperando());

      repository.save(toBeUpdated.get());

      return toBeUpdated.get();
    } catch (CustomNotFoundException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (CustomBadRequestException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por remover determinado drone.
   *
   * @param id - recebe o id do drone que será removido.
   */
  @Transactional
  public void removeDrone(Integer id) {

    try {
      Optional<Drone> toBeDeleted = repository.findById(id);

      if (toBeDeleted.isEmpty()) {
        throw new CustomNotFoundException("Drone não encontrado.");
      }

      repository.deleteById(id);
    } catch (CustomNotFoundException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }
}
