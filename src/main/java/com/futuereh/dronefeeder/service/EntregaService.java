package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.EntregaExistsException;
import com.futuereh.dronefeeder.commons.UnexpectedErrorException;
import com.futuereh.dronefeeder.dto.EntregaDto;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

/**
 * Classe responsável pelas validações que antecedem as operações com o DB.
 */
@Service
public class EntregaService {

  private EntregaRepository entregaRepository;

  public EntregaService(EntregaRepository entregaRepository) {
    this.entregaRepository = entregaRepository;
  }

  /**
   * Método responsável por adicionar uma nova entrega.
   *
   * @param entregaDto - recebe as informações referentes à nova entrega.
   * @return - retorna a entrega que foi adicionada.
   */
  public Entrega addEntrega(EntregaDto entregaDto) {

    try {
      if (entregaRepository.existsByDestinatario(entregaDto.getDestinatario())) {
        throw new EntregaExistsException();
      }

      String formatData = "dd/MM/yyyy";

      DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern(formatData);

      String formatHora = "HH:mm:ss";

      DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern(formatHora);

      LocalDateTime horaPrimeiraRefeicao = LocalDateTime.now();

      String dataEntrega = formatadorData.format(horaPrimeiraRefeicao)
          + " - " + formatadorHora.format(horaPrimeiraRefeicao);

      entregaDto.setData(dataEntrega);

      Entrega newEntrega = new Entrega(entregaDto.getBairro(), entregaDto.getCep(),
          entregaDto.getEndereco(), entregaDto.getNum(), entregaDto.getDestinatario(),
          entregaDto.getData(), entregaDto.isStatus());

      this.entregaRepository.save(newEntrega);

      return newEntrega;
    } catch (EntregaExistsException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }
}
