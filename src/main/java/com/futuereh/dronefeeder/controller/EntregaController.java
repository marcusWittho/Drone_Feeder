package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.dto.EntregaDto;
import com.futuereh.dronefeeder.service.EntregaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Classe responsável pelos endpoints da aplicação.
 */
@RestController
@RequestMapping("/entrega")
public class EntregaController {

  private EntregaService entregaService;

  public EntregaController(EntregaService entregaService) {

    this.entregaService = entregaService;
  }

  /**
   * Método responsável pelo endpoint que faz parte do fluxo de adição de uma nova entrega..
   *
   * @param entregaDto - recebe as informações referentes à nova entrega.
   * @return - retorna a entrega que foi adicionada.
   */
  @PostMapping("/add")
  public ResponseEntity addEntrega(@RequestBody EntregaDto entregaDto) {

    return ResponseEntity.status(HttpStatus.CREATED).body(entregaService.addEntrega(entregaDto));
  }

  /**
   * Método responsável por listar as entregas.
   */
  @GetMapping("/all")
  public ResponseEntity entregasList() {

    return ResponseEntity.status(HttpStatus.OK).body(entregaService.entregasList());
  }
}
