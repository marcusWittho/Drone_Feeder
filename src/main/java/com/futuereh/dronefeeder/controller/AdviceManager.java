package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.commons.DataError;
import com.futuereh.dronefeeder.commons.BadRequestException;
import com.futuereh.dronefeeder.commons.ExistsException;
import com.futuereh.dronefeeder.commons.UnexpectedErrorException;
import com.futuereh.dronefeeder.commons.NotFoundException;
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
  @ExceptionHandler({ExistsException.class})
  public ResponseEntity<DataError> handlerConflict(ExistsException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  /**
   * Captura exceções do tipo DroneNotFoundException.
   */
  @ExceptionHandler({NotFoundException.class})
  public ResponseEntity<DataError> handlerNotFound(NotFoundException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * Captura exceções do tipo DroneBadRequest.
   */
  @ExceptionHandler({BadRequestException.class})
  public ResponseEntity<DataError> handlerBadRequest(BadRequestException exception) {

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
}
