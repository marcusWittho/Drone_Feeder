package com.futuereh.dronefeeder.repository;

import com.futuereh.dronefeeder.model.Teste;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TesteRepository extends JpaRepository<Teste, Integer> {

  Teste findByName(String name);
}
