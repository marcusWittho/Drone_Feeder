package com.futuereh.dronefeeder.repository;

import com.futuereh.dronefeeder.model.Entrega;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Interface responsável por ceder os métodos necessários para manipulação do DB.
 */
@Repository
public interface EntregaRepository extends JpaRepository<Entrega, Integer> {

  boolean existsByDestinatario(String destinatario);
}
