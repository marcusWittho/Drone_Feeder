package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.dto.EntregaDto;
import com.futuereh.dronefeeder.service.EntregaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

  /**
   * Método responsável por buscar determinada entrega pelo id indicado.
   *
   * @param id - recebe o id da entrega a ser consultada.
   * @return - retorna a entrega referente ao id recebido por parâmetro.
   */
  @GetMapping("/{id}")
  public ResponseEntity entregaById(@PathVariable(value = "id") Integer id) {

    return ResponseEntity.status(HttpStatus.OK).body(entregaService.entregaById(id));
  }

  /**
   * Método responsável por atualizar determinada entrega.
   *
   * @param id - recebe o id da entrega que será atualizada.
   * @param entregaDto - recebe as novas informações para realizar a atualização.
   * @return - retorna a entrega atualizada.
   */
  @PutMapping("/{id}")
  public ResponseEntity updateEntrega(@PathVariable(value = "id") Integer id,
                                      @RequestBody EntregaDto entregaDto) {

    return ResponseEntity.status(HttpStatus.OK).body(entregaService.updateentrega(id, entregaDto));
  }

  /**
   * Método responsável por remover determinada entrega.
   *
   * @param id - recebe o id da entrega que será removida.
   * @return - não há retorno, somente o status http 200.
   */
  @DeleteMapping("/{id}")
  public ResponseEntity removeEntrega(@PathVariable(value = "id") Integer id) {

    entregaService.removeEntrega(id);

    return ResponseEntity.status(HttpStatus.OK).build();
  }
}
