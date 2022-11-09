package com.futuereh.dronefeeder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.futuereh.dronefeeder.commons.CustomBadRequestException;
import com.futuereh.dronefeeder.dto.DroneDto;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class DroneServiceTest {

  @Mock
  private DroneRepository repository;

  @InjectMocks
  private DroneService service;

  @Test
  @DisplayName("01 - Testa adição de novo drone com sucesso.")
  void addDroneComSucesso() {

    DroneDto droneDto = new DroneDto();

    droneDto.setSerialNumber("A300");
    droneDto.setLatitude(-10.123123);
    droneDto.setLongitude(-10.123123);
    droneDto.setOperando(false);

    Drone drone = service.addDrone(droneDto);

    assertEquals("A300", drone.getSerialNumber());
  }

  @Test
  @DisplayName("02 - Testa tentativa de adição com serialNumber faltante.")
  void addDroneComSerialFaltante() {

    DroneDto droneDto = new DroneDto();

    droneDto.setSerialNumber("");
    droneDto.setLatitude(-10.123123);
    droneDto.setLongitude(-10.123123);
    droneDto.setOperando(false);

    CustomBadRequestException thrown = assertThrows(
      CustomBadRequestException.class,
      () -> service.addDrone(droneDto),
      "SerialNumber não foi informado."
    );

    assertTrue(thrown.getMessage().contains("SerialNumber não foi informado."));
  }

  @Test
  @DisplayName("03 - Testa tentativa de adição com Latitude faltante.")
  void addDroneComLatitudeFaltante() {

    DroneDto droneDto = new DroneDto();

    droneDto.setSerialNumber("A300");
    droneDto.setLongitude(-10.123123);
    droneDto.setOperando(false);

    CustomBadRequestException thrown = assertThrows(
      CustomBadRequestException.class,
      () -> service.addDrone(droneDto),
      "Latitude não foi informada."
    );

    assertTrue(thrown.getMessage().contains("Latitude não foi informada."));
  }

  @Test
  @DisplayName("04 - Testa tentativa de adição com Longitude faltante.")
  void addDroneComLongitudeFaltante() {

    DroneDto droneDto = new DroneDto();

    droneDto.setSerialNumber("A300");
    droneDto.setLatitude(-10.123123);
    droneDto.setOperando(false);

    CustomBadRequestException thrown = assertThrows(
      CustomBadRequestException.class,
      () -> service.addDrone(droneDto),
      "Longitude não foi informada."
    );

    assertTrue(thrown.getMessage().contains("Longitude não foi informada."));
  }

  @Test
  @DisplayName("05 - Testa retorno de lista com os drones cadastrados")
  void retornaListadeDrones() {

    List<Drone> drones = service.drones();

    assertNotNull(drones);
  }

  @Test
  @DisplayName("06 - Testa a busca de drone pelo id.")
  void buscaDronePeloId() {

    Drone droneMock = new Drone("A200", -10.123123, -10.123123, false);

    repository.save(droneMock);

    Optional<Drone> drone = repository.findById(droneMock.getId());

    assertNotNull(drone);
  }

  @Test
  @DisplayName("07 - Testa retorno dos drones que estão livres.")
  void listaDronesLivres() {

    List<Drone> drones = repository.findIsOperandoFalse();

    assertNotNull(drones);
  }

  @Test
  @DisplayName("08 - Testa a remoção de determinado drone.")
  void updateDrone() {

    Drone droneMock = new Drone("A200", -10.123123, -10.123123, false);

    this.repository.deleteById(droneMock.getId());

    Optional<Drone> produto = repository.findById(droneMock.getId());

    assertEquals(produto, Optional.empty());
  }
}
