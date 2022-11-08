package com.futuereh.dronefeeder.controller;

import com.futuereh.dronefeeder.commons.DataError;
import com.futuereh.dronefeeder.commons.CustomBadRequestException;
import com.futuereh.dronefeeder.commons.CustomExistsException;
import com.futuereh.dronefeeder.commons.CustomUnexpectedErrorException;
import com.futuereh.dronefeeder.commons.CustomNotFoundException;
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
  @ExceptionHandler({CustomExistsException.class})
  public ResponseEntity<DataError> handlerConflict(CustomExistsException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
  }

  /**
   * Captura exceções do tipo DroneNotFoundException.
   */
  @ExceptionHandler({CustomNotFoundException.class})
  public ResponseEntity<DataError> handlerNotFound(CustomNotFoundException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
  }

  /**
   * Captura exceções do tipo DroneBadRequest.
   */
  @ExceptionHandler({CustomBadRequestException.class})
  public ResponseEntity<DataError> handlerBadRequest(CustomBadRequestException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
  }

  /**
   * Captura exceções do tipo ErroInesperado.
   */
  @ExceptionHandler({CustomUnexpectedErrorException.class})
  public ResponseEntity<DataError> handlerBadRequest(CustomUnexpectedErrorException exception) {

    DataError error = new DataError(exception.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
  }
}
