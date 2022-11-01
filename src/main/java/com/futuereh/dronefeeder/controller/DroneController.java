package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.model.Drone;
import com.futuereh.dronefeeder.service.DroneService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public Drone addDrone(@RequestBody Drone drone) {

    Drone newDrone = new Drone(
        drone.getSerialNumber(), drone.getLatitude(), drone.getLongitude(), drone.isOperando());

    return service.addDrone(newDrone);
  }
}
