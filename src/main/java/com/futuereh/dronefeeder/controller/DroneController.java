package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.dto.DroneDto;
import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.service.DroneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * classe responsável pelos endpoints da aplicação.
 */
@RestController
@RequestMapping("/drone")
public class DroneController {

  private DroneService service;

  public DroneController(DroneService service) {

    this.service = service;
  }

  /**
   * Método responsável pelo endpoint que faz parte do fluxo de adição de uma novo drone.
   *
   * <p>@param drone - recebe as informações do drone que será adicionado ao DB.</p>
   * <p>@return - retorna uma String com as informações do drone adicionado ao DB.</p>
   */
  @PostMapping("/add")
  public ResponseEntity addDrone(@RequestBody DroneDto droneDto) {

    return ResponseEntity.status(HttpStatus.CREATED).body(service.addDrone(droneDto));
  }

  /**
   * Método responsável por buscar determinado drone pelo id indicado.
   *
   * @param id - recebe o id do drone a ser consultado.
   * @return - retorna o drone referente ao id recebido por parâmetro.
   */
  @GetMapping("/{id}")
  public ResponseEntity droneById(@PathVariable(value = "id") Integer id) {

    return ResponseEntity.status(HttpStatus.OK).body(service.droneById(id));
  }
}
