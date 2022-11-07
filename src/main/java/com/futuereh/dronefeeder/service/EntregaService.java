package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.ExistsException;
import com.futuereh.dronefeeder.commons.NotFoundException;
import com.futuereh.dronefeeder.commons.UnexpectedErrorException;
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
   * Método responsável por adicionar uma nova entrega.
   *
   * @param entregaDto - recebe as informações referentes à nova entrega.
   * @return - retorna a entrega que foi adicionada.
   */
  public Entrega addEntrega(EntregaDto entregaDto) {

    try {
      if (entregaRepository.existsByDestinatario(entregaDto.getDestinatario())) {

        throw new ExistsException("Entrega já está cadastrada.");
      }

      drones = droneService.droneByStatusFalse();

      if (drones.isEmpty()) {

        throw new NotFoundException("Drone não encontrado.");
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
    } catch (ExistsException err) {
      logger.warn("Error Message: " + err.getMessage());
      throw err;
    } catch (NotFoundException err) {
      logger.warn("Error Message: " + err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.warn("Error Message: " + err.getMessage());
      throw new UnexpectedErrorException();
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
      logger.warn("Error Message: " + err.getMessage());
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
        throw new NotFoundException("Entrega não encontrada.");
      }

      return entrega.get();
    } catch (NotFoundException err) {
      logger.warn(err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.warn(err.getMessage());
      throw new UnexpectedErrorException();
    }
  }

  /**
   * Método responsável por atualizar determinada entrega.
   *
   * @param id - recebe o id da entrega que será atualizada.
   * @param entregaDto - recebe as novas informações da entrega.
   * @return - retorna a entrega com as informaçẽos atualizadas.
   */
  public Entrega updateentrega(Integer id, EntregaDto entregaDto) {

    try {

      Optional<Entrega> toBeUpdated = entregaRepository.findById(id);

      if (toBeUpdated.isEmpty()) {
        throw new NotFoundException("Entrega não encontrada.");
      }

      toBeUpdated.get().setBairro(entregaDto.getBairro());
      toBeUpdated.get().setCep(entregaDto.getCep());
      toBeUpdated.get().setEndereco(entregaDto.getEndereco());
      toBeUpdated.get().setNum(entregaDto.getNum());
      toBeUpdated.get().setDestinatario(entregaDto.getDestinatario());
      toBeUpdated.get().setData(entregaDto.getData());
      toBeUpdated.get().setStatus(entregaDto.isStatus());

      entregaRepository.save(toBeUpdated.get());

      return toBeUpdated.get();
    } catch (NotFoundException err) {
      logger.warn(err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.warn(err.getMessage());
      throw new UnexpectedErrorException();
    }
  }

  /**
   * Método responsável por remover determinada entrega.
   *
   * @param id - recebe o id da entrega que será removida.
   */
  public void removeEntrega(Integer id) {

    try {
      Optional<Entrega> toBeDeleted = entregaRepository.findById(id);

      if (toBeDeleted.isEmpty()) {
        throw new NotFoundException("Entrega não encontrada.");
      }

      entregaRepository.deleteById(id);
    } catch (NotFoundException err) {
      logger.warn(err.getMessage());
      throw err;
    } catch (Exception err) {
      logger.warn(err.getMessage());
      throw new UnexpectedErrorException();
    }
  }
}
