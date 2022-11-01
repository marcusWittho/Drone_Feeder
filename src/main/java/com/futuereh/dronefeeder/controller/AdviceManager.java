package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.commons.DataError;
import com.futuereh.dronefeeder.commons.DroneExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Método que faz o gerenciamento das exceções geradas.
 */
@ControllerAdvice
public class AdviceManager {

  /**
   * Captura exceções do tipo DroneExistsException.
   */
  @ExceptionHandler({DroneExistsException.class})
  public ResponseEntity<DataError> handlerConflict(DroneExistsException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }
}
