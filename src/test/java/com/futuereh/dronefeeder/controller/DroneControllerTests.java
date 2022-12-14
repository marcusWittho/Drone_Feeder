package com.futuereh.dronefeeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futuereh.dronefeeder.commons.CustomNotFoundException;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.repository.DroneRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
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
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
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
  @Order(1)
  @DisplayName("01 - Testa a adi????o de novos drones ao DB.")
  void adicionarNovoDrone() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    mockMvc
      .perform(post("/drone/add").contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone)));

    verify(droneRepository, atLeast(1)).save(serieCaptor.capture());

    assertThat(serieCaptor.getValue()).isNotNull();
    assertThat(serieCaptor.getValue().getId()).isNotNull();
    assertThat(serieCaptor.getValue().getSerialNumber()).isEqualTo(newDrone.getSerialNumber());
    assertThat(serieCaptor.getValue().getLatitude()).isEqualTo(newDrone.getLatitude());
    assertThat(serieCaptor.getValue().getLongitude()).isEqualTo(newDrone.getLongitude());
    assertThat(serieCaptor.getValue().isOperando()).isEqualTo(newDrone.isOperando());
    assertThat(serieCaptor.getValue().getEntregas().size()).isEqualTo(newDrone.getEntregas().size());
  }

  @Test
  @Order(2)
  @DisplayName("02 - Testa adi????o de novo drone sem informar o serialNumber.")
  void adicionarNovoDroneSemSerialNumber() throws Exception {

    Drone newDrone = new Drone("", -46.761107, -23.5747372, true);

    mockMvc
      .perform(post("/drone/add").contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone)))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isBadRequest());
  }

  @Test
  @Order(3)
  @DisplayName("03 - Testa caso de erro inesperado.")
  void adicionarNovoDroneErroInesperado() throws Exception {

    Drone newDrone = new Drone();

    mockMvc
      .perform(post("/drone/add").contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone)))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isInternalServerError()).andExpect(jsonPath("$.error").value("Erro inesperado."));
  }

  @Test
  @Order(4)
  @DisplayName("04 - Testa caso de drone a ser adicionado j?? existir no DB.")
  void adicionarDroneExistente() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    droneRepository.save(newDrone);

    mockMvc
      .perform(post("/drone/add").contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone)))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
      .andExpect(status().isConflict()).andExpect(jsonPath("$.error").value("Drone j?? cadastrado."));
  }

  @Test
  @Order(5)
  @DisplayName("05 - Testa o retorno da busca por id.")
  void buscarDronePorId() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    droneRepository.save(newDrone);

    mockMvc
      .perform(get("/drone/" + newDrone.getId()).contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
      .andExpect(jsonPath("$.serialNumber").value(newDrone.getSerialNumber()));
  }

  @Test
  @Order(6)
  @DisplayName("06 - Testa caso em que n??o ?? encontrado drone com o id informado.")
  void dronePorIdNotFound() throws Exception {

    mockMvc
      .perform(get("/drone/0").contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNotFound())
      .andExpect(status().isNotFound()).andExpect(jsonPath("$.error").value("Drone n??o encontrado."));
  }

  @Test
  @Order(7)
  @DisplayName("07 - Testa retorno de drones com status false.")
  void dronesLivres() throws Exception {

    Drone newDrone_1 = new Drone("A100", -46.761107, -23.5747372, false);
    Drone newDrone_2 = new Drone("A200", -46.761107, -23.5747372, false);

    droneRepository.save(newDrone_1);
    droneRepository.save(newDrone_2);

    mockMvc
      .perform(get("/drone/livre").contentType(MediaType.APPLICATION_JSON))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
      .andExpect(jsonPath("$[0].serialNumber").value(newDrone_1.getSerialNumber()))
      .andExpect(jsonPath("$[1].serialNumber").value(newDrone_2.getSerialNumber()));
  }

  @Test
  @Order(8)
  @DisplayName("08 - Testa retorno de drones com status false n??o existirem.")
  void dronesLivresInexistentes() throws Exception {

    Drone newDrone_1 = new Drone("A100", -46.761107, -23.5747372, true);

    droneRepository.save(newDrone_1);

    when(get("/drone/livre")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone_1)))
      .thenThrow(CustomNotFoundException.class);

    mockMvc
      .perform(get("/drone/livre").contentType(MediaType.APPLICATION_JSON));
  }

  @Test
  @Order(9)
  @DisplayName("09 - Testa se determinado drone foi atualizado.")
  void atualizarDrone() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    droneRepository.save(newDrone);

    mockMvc
      .perform(put("/drone/" + newDrone.getId()).contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newDrone)))
      .andExpect(content().contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
      .andExpect(jsonPath("$.serialNumber").value(newDrone.getSerialNumber()))
      .andExpect(jsonPath("$.latitude").value(newDrone.getLatitude()))
      .andExpect(jsonPath("$.longitude").value(newDrone.getLongitude()))
      .andExpect(jsonPath("$.operando").value(newDrone.isOperando()));
  }

  @Test
  @Order(10)
  @DisplayName("10 - Testa se a remo????o do drone ocorreu com sucesso.")
  void removeDrone() throws Exception {

    Drone newDrone = new Drone("A100", -46.761107, -23.5747372, true);

    droneRepository.save(newDrone);

    mockMvc
      .perform(delete("/drone/" + newDrone.getId()))
      .andExpect(status().isOk());
  }

  @Test
  @Order(11)
  @DisplayName("11 - Testa a tentativa de remo????o de um drone inexistente.")
  void droneNaoEncontradoParaRemocao() throws Exception {

    mockMvc
      .perform(delete("/drone/0"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.error").value("Drone n??o encontrado."));
  }
}