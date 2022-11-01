package com.futuereh.dronefeeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DroneControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private DroneRepository droneRepository;

  @Captor
  private ArgumentCaptor<Drone> serieCaptor;

  @BeforeEach
  public void setup() {
    droneRepository.deleteAll();
  }

  @Test
  @DisplayName("01 - Testa a adição de novos drones ao DB.")
  void adicionarNovoDrone() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    mockMvc
      .perform(post("/drone/add").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newDrone)))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
      .andExpect(jsonPath("$.serialNumber").value(newDrone.getSerialNumber()))
      .andExpect(jsonPath("$.latitude").value(newDrone.getLatitude()))
      .andExpect(jsonPath("$.longitude").value(newDrone.getLongitude()))
      .andExpect(jsonPath("$.operando").value(newDrone.isOperando()));

    verify(droneRepository, atLeast(1)).save(serieCaptor.capture());

    assertThat(serieCaptor.getValue()).isNotNull();
    assertThat(serieCaptor.getValue().getId()).isNotNull();
    assertThat(serieCaptor.getValue().getSerialNumber()).isEqualTo(newDrone.getSerialNumber());
    assertThat(serieCaptor.getValue().getLatitude()).isEqualTo(newDrone.getLatitude());
    assertThat(serieCaptor.getValue().getLongitude()).isEqualTo(newDrone.getLongitude());
    assertThat(serieCaptor.getValue().isOperando()).isEqualTo(newDrone.isOperando());
  }

  @Test
  @DisplayName("02 - Testa caso de drone a ser adicionado já existir no DB.")
  void adicionarDroneExistente() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    droneRepository.save(newDrone);

    mockMvc
      .perform(post("/drone/add").contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone)))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isConflict()).andExpect(jsonPath("$.error").value("Drone já cadastrado."));
  }
}
