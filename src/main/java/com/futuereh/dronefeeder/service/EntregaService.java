package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.commons.DroneNotFoundException;
import com.futuereh.dronefeeder.commons.EntregaExistsException;
import com.futuereh.dronefeeder.commons.EntregaNotFoundException;
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
        throw new EntregaExistsException();
      }

      drones = droneService.droneByStatusFalse();

      if (drones.isEmpty()) {
        throw new DroneNotFoundException();
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
    } catch (EntregaExistsException err) {
      throw err;
    } catch (DroneNotFoundException err) {

      throw err;
    } catch (Exception err) {
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
        throw new EntregaNotFoundException();
      }

      return entrega.get();
    } catch (EntregaNotFoundException err) {
      throw err;
    } catch (Exception err) {
      throw new UnexpectedErrorException();
    }
  }
}
