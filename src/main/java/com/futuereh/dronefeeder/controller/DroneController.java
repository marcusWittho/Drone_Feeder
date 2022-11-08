package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.dto.DroneDto;
import com.futuereh.dronefeeder.service.DroneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * classe responsável pelos endpoints da aplicação.
 */
@RestController
@RequestMapping("/")
public class DroneController {

  private DroneService service;

  public DroneController(DroneService service) {

    this.service = service;
  }

  @GetMapping("/")
  public ResponseEntity helloDrone() {

    return ResponseEntity.status(HttpStatus.OK).body("Hello Drone!");
  }
  /**
   * Método responsável pelo endpoint que faz parte do fluxo de adição de um novo drone.
   *
   * @return - retorna uma String com as informações do drone adicionado ao DB.
   */
  @PostMapping("/drone/add")
  public ResponseEntity addDrone(@RequestBody DroneDto droneDto) {

    return ResponseEntity.status(HttpStatus.CREATED).body(service.addDrone(droneDto));
  }

  /**
   * Método responsável por buscar determinado drone pelo id indicado.
   *
   * @param id - recebe o id do drone a ser consultado.
   * @return - retorna o drone referente ao id recebido por parâmetro.
   */
  @GetMapping("/drone/{id}")
  public ResponseEntity droneById(@PathVariable(value = "id") String id) {

    return ResponseEntity.status(HttpStatus.OK).body(service.droneById(Integer.parseInt(id)));
  }

  /**
   * Método responsável por buscar drones desocupados.
   *
   * @return - retorna uma lista de drones.
   */
  @GetMapping("/drone/livre")
  public ResponseEntity droneByStatus() {

    return ResponseEntity.status(HttpStatus.OK).body(service.droneByStatusFalse());
  }

  /**
   * Método responsável por atualizar determinado drone.
   *
   * @param id - recebe o id do drone que será atualizado.
   * @param droneDto - recebe as novas informações para realizar a atualização.
   * @return - retorna o drone atualizado.
   */
  @PutMapping("/drone/{id}")
  public ResponseEntity updateDrone(@PathVariable(value = "id") Integer id,
                                    @RequestBody DroneDto droneDto) {

    return ResponseEntity.status(HttpStatus.OK).body(service.updateDrone(id, droneDto));
  }

  /**
   * Método responsável por remover determinado drone.
   *
   * @param id - recebe o id do drone que será removido.
   * @return - não há retorno, somente o status http 200.
   */
  @DeleteMapping("/drone/{id}")
  public ResponseEntity removeDrone(@PathVariable(value = "id") Integer id) {

    service.removeDrone(id);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
