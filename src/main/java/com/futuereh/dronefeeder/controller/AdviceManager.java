package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.commons.DataError;
import com.futuereh.dronefeeder.commons.DroneBadRequest;
import com.futuereh.dronefeeder.commons.DroneExistsException;
import com.futuereh.dronefeeder.commons.DroneNotFoundException;
import org.springframework.core.NestedCheckedException;
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

  /**
   * Captura exceções do tipo DroneNotFoundException.
   */
  @ExceptionHandler({DroneNotFoundException.class})
  public ResponseEntity<DataError> handlerNotFound(DroneNotFoundException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * Captura exceções do tipo DroneBadRequest.
   */
  @ExceptionHandler({DroneBadRequest.class})
  public ResponseEntity<DataError> handlerBadRequest(DroneBadRequest exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }
}
