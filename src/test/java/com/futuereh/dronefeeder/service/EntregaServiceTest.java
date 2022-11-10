package com.futuereh.dronefeeder.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.futuereh.dronefeeder.dto.EntregaDto;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EntregaServiceTest {

  @Mock
  private EntregaRepository repository;

  @InjectMocks
  private EntregaService service;

  @Test
  @DisplayName("01 - Testa caso de sucesso para o método validarEntrega.")
  void validarEntrega() {

    EntregaDto entregaDto = new EntregaDto();

    entregaDto.setBairro("bairro");
    entregaDto.setCep("cep");
    entregaDto.setEndereco("endereço");
    entregaDto.setNum(1);
    entregaDto.setDestinatario("destinatário");
    entregaDto.setData("data");
    entregaDto.setStatus(false);

    String result = service.validarEntrega(entregaDto);

    assertEquals("", result);
  }

  @Test
  @DisplayName("02 - Testa retorno de lista com as entregas cadastradas. ")
  void retornaListaDeEntregas() {

    List<Entrega> entregas = service.entregasList();

    assertNotNull(entregas);
  }

  @Test
  @DisplayName("03 - Testa a busca de entrega pelo id.")
  void buscaEntregaPeloId() {

    Entrega entregaMock = new Entrega(
      "bairro",
      "cep",
      "endereco",
      1,
      "destinatã́rio",
      "data",
      false,
      null
    );

    repository.save(entregaMock);

    Optional<Entrega> entrega = repository.findById(entregaMock.getId());

    assertNotNull(entrega);
  }

  @Test
  @DisplayName("04 - Testa a remoção de determinada entrega.")
  void removeEntrega() {

    Entrega entregaMock = new Entrega(
      "bairro",
      "cep",
      "endereco",
      1,
      "destinatã́rio",
      "data",
      false,
      null
    );

    repository.save(entregaMock);

    repository.deleteById(entregaMock.getId());

    Optional<Entrega> entrega = repository.findById(entregaMock.getId());

    assertEquals(entrega, Optional.empty());
  }
}
