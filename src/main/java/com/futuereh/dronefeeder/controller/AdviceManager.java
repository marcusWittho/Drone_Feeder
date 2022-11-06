package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.commons.DataError;
import com.futuereh.dronefeeder.commons.DroneBadRequestException;
import com.futuereh.dronefeeder.commons.DroneExistsException;
import com.futuereh.dronefeeder.commons.EntregaExistsException;
import com.futuereh.dronefeeder.commons.EntregaNotFoundException;
import com.futuereh.dronefeeder.commons.UnexpectedErrorException;
import com.futuereh.dronefeeder.commons.DroneNotFoundException;
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
  @ExceptionHandler({DroneBadRequestException.class})
  public ResponseEntity<DataError> handlerBadRequest(DroneBadRequestException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  /**
   * Captura exceções do tipo ErroInesperado.
   */
  @ExceptionHandler({UnexpectedErrorException.class})
  public ResponseEntity<DataError> handlerBadRequest(UnexpectedErrorException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }

  /**
   * Captura exceções do tipo CONFLICT.
   */
  @ExceptionHandler({EntregaExistsException.class})
  public ResponseEntity<DataError> handlerBadRequest(EntregaExistsException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  /**
   * Captura exceções do tipo DroneNotFoundException.
   */
  @ExceptionHandler({EntregaNotFoundException.class})
  public ResponseEntity<DataError> handlerNotFound(EntregaNotFoundException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }
}
