package com.futuereh.dronefeeder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
  @DisplayName("02 - Testa adição de novo drone já cadastrado.")
  void addDroneJaCadastrado() {

    DroneDto droneDto = new DroneDto();

    droneDto.setSerialNumber("A300");
    droneDto.setLatitude(-10.123123);
    droneDto.setLongitude(-10.123123);
    droneDto.setOperando(false);

    service.addDrone(droneDto);

    CustomBadRequestException thrown = assertThrows(
      CustomBadRequestException.class,
      () -> service.addDrone(droneDto),
      "Drone já cadastrado."
    );

    assertTrue(thrown.getMessage().contains("Drone já cadastrado."));
  }

  @Test
  @DisplayName("03 - Testa tentativa de adição com serialNumber faltante.")
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
  @DisplayName("04 - Testa tentativa de adição com Latitude faltante.")
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
  @DisplayName("05 - Testa tentativa de adição com Longitude faltante.")
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
}
