package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.CustomBadRequestException;
import com.futuereh.dronefeeder.commons.CustomExistsException;
import com.futuereh.dronefeeder.commons.CustomNotFoundException;
import com.futuereh.dronefeeder.commons.CustomUnexpectedErrorException;
import com.futuereh.dronefeeder.dto.EntregaDto;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.model.Entrega;
import com.futuereh.dronefeeder.repository.DroneRepository;
import com.futuereh.dronefeeder.repository.EntregaRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

/**
 * Classe responsável pelas validações que antecedem as operações com o DB.
 */
@Service
public class EntregaService {

  private List<Drone> drones;

  private EntregaRepository entregaRepository;

  private DroneService droneService;

  private DroneRepository droneRepository;

  private final Logger logger = Logger.getLogger(EntregaService.class);

  /**
   * Injeção de dependências.
   */
  public EntregaService(EntregaRepository entregaRepository, DroneService droneService,
                        DroneRepository droneRepository) {
    this.entregaRepository = entregaRepository;
    this.droneService = droneService;
    this.droneRepository = droneRepository;
  }

  /**
   * Validação simples dos dados de entreda.
   */
  public String validarEntrega(EntregaDto entregaDto) {
    if (entregaDto.getBairro().isEmpty()) {

      return "O bairro não foi informado.";
    }

    if (entregaDto.getCep().isEmpty()) {

      return "O cep não foi informado.";
    }

    if (entregaDto.getEndereco().isEmpty()) {

      return "O endereço não foi informado.";
    }

    if (entregaDto.getNum() == null) {

      return "O número não foi informado.";
    }

    if (entregaDto.getDestinatario().isEmpty()) {

      return "O destinatário não foi informado.";
    }

    if (entregaDto.getDestinatario().isEmpty()) {

      return "O destinatário não foi informado.";
    }

    return "";
  }

  /**
   * Método responsável por adicionar uma nova entrega.
   *
   * @param entregaDto - recebe as informações referentes à nova entrega.
   * @return - retorna a entrega que foi adicionada.
   */
  @Transactional
  public Entrega addEntrega(EntregaDto entregaDto) {

    try {
      drones = droneService.droneByStatusFalse();

      if (drones.isEmpty()) {

        throw new CustomNotFoundException("Não há drone disponível.");
      }

      if (!validarEntrega(entregaDto).isEmpty()) {
        throw new CustomBadRequestException(validarEntrega(entregaDto));
      }

      if (entregaRepository.existsByDestinatario(entregaDto.getDestinatario())) {

        throw new CustomExistsException("Entrega já está cadastrada.");
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

      drones.get(0).setOperando(true);

      newEntrega.setDrone(drones.get(0));
      drones.get(0).addEntrega(newEntrega);
      droneRepository.save(drones.get(0));

      return newEntrega;
    } catch (CustomExistsException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (CustomBadRequestException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (CustomNotFoundException err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por listar todas as entregas.
   */
  public List<Entrega> entregasList() {

    try {
      List<Entrega> entregas = entregaRepository.findAll();

      return entregas;
    } catch (Exception err) {
      logger.error("Error Message: " + err.getMessage());
      throw err;
    }
  }

  /**
   * Método responsável por retornar uma entrega de acordo com o id informado por parâmetro.
   *
   * @param id - recebe o id referente à entrega que será objeto da busca.
   * @return - retorna uma entrega de acordo com o id informado.
   */
  public Entrega entregaById(Integer id) {

    try {
      Optional<Entrega> entrega = entregaRepository.findById(id);

      if (entrega.isEmpty()) {
        throw new CustomNotFoundException("Entrega não encontrada.");
      }

      return entrega.get();
    } catch (CustomNotFoundException err) {
      logger.error(err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error(err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por atualizar determinada entrega.
   *
   * @param id - recebe o id da entrega que será atualizada.
   * @param entregaDto - recebe as novas informações da entrega.
   * @return - retorna a entrega com as informaçẽos atualizadas.
   */
  @Transactional
  public Entrega updateEntrega(Integer id, EntregaDto entregaDto) {

    try {

      Optional<Entrega> toBeUpdated = entregaRepository.findById(id);

      if (toBeUpdated.isEmpty()) {
        throw new CustomNotFoundException("Entrega não encontrada.");
      }

      String formatData = "dd/MM/yyyy";

      DateTimeFormatter formatadorData = DateTimeFormatter.ofPattern(formatData);

      String formatHora = "HH:mm:ss";

      DateTimeFormatter formatadorHora = DateTimeFormatter.ofPattern(formatHora);

      LocalDateTime horaPrimeiraRefeicao = LocalDateTime.now();

      String dataEntrega = formatadorData.format(horaPrimeiraRefeicao)
        + " - " + formatadorHora.format(horaPrimeiraRefeicao);

      toBeUpdated.get().setBairro(entregaDto.getBairro());
      toBeUpdated.get().setCep(entregaDto.getCep());
      toBeUpdated.get().setEndereco(entregaDto.getEndereco());
      toBeUpdated.get().setNum(entregaDto.getNum());
      toBeUpdated.get().setDestinatario(entregaDto.getDestinatario());
      toBeUpdated.get().setData(dataEntrega);
      toBeUpdated.get().setStatus(entregaDto.isStatus());

      entregaRepository.save(toBeUpdated.get());

      return toBeUpdated.get();
    } catch (CustomNotFoundException err) {
      logger.error(err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error(err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }

  /**
   * Método responsável por remover determinada entrega.
   *
   * @param id - recebe o id da entrega que será removida.
   */
  @Transactional
  public void removeEntrega(Integer id) {

    try {
      Optional<Entrega> toBeDeleted = entregaRepository.findById(id);

      if (toBeDeleted.isEmpty()) {
        throw new CustomNotFoundException("Entrega não encontrada.");
      }

      entregaRepository.deleteById(id);
    } catch (CustomNotFoundException err) {
      logger.error(err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.error(err.getMessage());
      throw new CustomUnexpectedErrorException();
    }
  }
}
