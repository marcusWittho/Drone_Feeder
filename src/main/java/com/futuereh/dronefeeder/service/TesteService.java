package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.model.Teste;
import com.futuereh.dronefeeder.repository.TesteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TesteService {

  private TesteRepository repository;

  public TesteService(TesteRepository repository) {
    this.repository = repository;
  }

  public Teste addTeste(Teste teste) {

    repository.save(teste);

    return teste;
  }

  public List<Teste> testeList() {

    return repository.findAll();
  }
}
