package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.model.Teste;
import com.futuereh.dronefeeder.service.TesteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TesteController {

  private TesteService service;

  public TesteController(TesteService service) {
    this.service = service;
  }

  @PostMapping("/add")
  public Teste addTeste(@RequestBody Teste teste) {

    Teste newTeste = new Teste();

    newTeste.setName(teste.getName());

    return service.addTeste(newTeste);
  }

  @GetMapping("/list")
  public List<Teste> testeList(){

    List<Teste> namesList = service.testeList();

    return namesList;
  }
}
