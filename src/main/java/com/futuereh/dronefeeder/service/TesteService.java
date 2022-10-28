package com.futuereh.dronefeeder.service;

import com.futuereh.dronefeeder.model.Teste;
import com.futuereh.dronefeeder.repository.TesteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TesteService {

  private TesteRepository repository;

  public TesteService() {}

  public TesteService(TesteRepository repository) {
    this.repository = repository;
  }

  public Teste addTeste(Teste teste) {

    return repository.save(teste);
  }
}
