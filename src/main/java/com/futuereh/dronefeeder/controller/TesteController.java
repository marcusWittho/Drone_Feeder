package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.model.Teste;
import com.futuereh.dronefeeder.service.TesteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/home")
public class TesteController {

  private TesteService service;

  public TesteController(TesteService service) {
    this.service = service;
  }

  public TesteController() {}

  @GetMapping(path = "/hello")
  public String hello() {
    return "Hello Spring";
  }

  @PostMapping
  public Teste addTeste(@RequestBody Teste teste) {

    return service.addTeste(teste);
  }
}
