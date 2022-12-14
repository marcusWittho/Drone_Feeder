package com.futuereh.dronefeeder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.futuereh.dronefeeder.commons.CustomExistsException;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.DroneRepository;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EntregaControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @SpyBean
  private EntregaRepository entregaRepository;

  @SpyBean
  private DroneRepository droneRepository;

  @Captor
  private ArgumentCaptor<Entrega> serieCaptor;

  String formatData = "dd/MM/yyyy";

  DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern(formatData);

  String formatHora = "HH:mm:ss";

  DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern(formatHora);

  LocalDateTime horaPrimeiraRefeicao = LocalDateTime.now();

  String dataEntrega = formatadorData.format(horaPrimeiraRefeicao) + " - " + formatadorHora.format(horaPrimeiraRefeicao);

  @BeforeEach
  public void setup() {
    entregaRepository.deleteAll();
  }

  @Test
  @Order(1)
  @DisplayName("01 - Testa a adi????o de novas entregas ao DB.")
  void adicionarNovaEntrega() throws Exception {

    Entrega newEntrega = new Entrega("bairro", "cep", "rua", 1,
      "destinatario", dataEntrega, false, null);

    mockMvc
      .perform(post("/entrega/add").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newEntrega)));

    entregaRepository.save(newEntrega);

    verify(entregaRepository, atLeast(1)).save(serieCaptor.capture());

    assertThat(serieCaptor.getValue()).isNotNull();
    assertThat(serieCaptor.getValue().getId()).isNotNull();
    assertThat(serieCaptor.getValue().getBairro()).isEqualTo(newEntrega.getBairro());
    assertThat(serieCaptor.getValue().getCep()).isEqualTo(newEntrega.getCep());
    assertThat(serieCaptor.getValue().getEndereco()).isEqualTo(newEntrega.getEndereco());
    assertThat(serieCaptor.getValue().getNum()).isEqualTo(newEntrega.getNum());
    assertThat(serieCaptor.getValue().getDestinatario()).isEqualTo(newEntrega.getDestinatario());
    assertThat(serieCaptor.getValue().getData()).isEqualTo(newEntrega.getData());
    assertThat(serieCaptor.getValue().isStatus()).isEqualTo(newEntrega.isStatus());
  }

  @Test
  @Order(2)
  @DisplayName("02 - Testa inser????o de entrega j?? cadastrada.")
  void deveRetornarEntregaExistsException() throws Exception {

    Entrega newEntrega = new Entrega("bairro", "cep", "rua", 1,
      "destinatario", dataEntrega, false, null);

    when(post("/entrega/add")
      .contentType(MediaType.APPLICATION_JSON)
      .content(new ObjectMapper().writeValueAsString(newEntrega)))
      .thenThrow(CustomExistsException.class);

    mockMvc
      .perform(post("/entrega/add").contentType(MediaType.APPLICATION_JSON)
        .content(new ObjectMapper().writeValueAsString(newEntrega)));
  }
}
