package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.dto.EntregaDto;
import com.futuereh.dronefeeder.service.EntregaService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Classe responsável pelos endpoints da aplicação.
 */
@RestController
@RequestMapping("/entrega")
public class EntregaController {

  private EntregaService entregaService;

  /**
   * Construtor da classe EntregaController.
   */
  public EntregaController(EntregaService entregaService) {

    this.entregaService = entregaService;
  }

  private final Logger logger = Logger.getLogger(EntregaService.class);

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

    return ResponseEntity.status(HttpStatus.OK).body(entregaService.updateEntrega(id, entregaDto));
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

  /**
   * Método responsável por alterar o status de determinada entrega.
   *
   * @param id - recebe o id da entrega que será alterada.
   * @return - não há retorno, somente o status http 200.
   */
  @PatchMapping("/{id}")
  public ResponseEntity alterarStatus(@PathVariable Integer id) {

    entregaService.alterarStatus(id);

    return ResponseEntity.status(HttpStatus.OK).build();
  }

  /**
   * Método responsável pelo upload do vídeo.
   */
  @PostMapping("/video")
  public ResponseEntity<String> salvarArquivo(@RequestParam MultipartFile file) throws IOException {

    entregaService.salvarVideo(file);

    return ResponseEntity.status(HttpStatus.OK).body("Arquivo enviado com sucesso.");
  }
}
