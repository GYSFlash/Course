package com.hotel.exceptions;


import com.hotel.dto.ErrorDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalHandlerException {
    @ExceptionHandler(NoIllegalArgumentException.class)
    public ResponseEntity<ErrorDTO> handleNoIllegalArgument(NoIllegalArgumentException e){
        ErrorDTO error = new ErrorDTO(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorDTO> handleNotFound(NotFoundException e){
        ErrorDTO error = new ErrorDTO(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleOtherExceptions(Exception ex) {
        ErrorDTO error = new ErrorDTO("Внутренняя ошибка сервера", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @ExceptionHandler({ AccessDeniedException.class })
    public ResponseEntity<ErrorDTO> handleAccessDenied(AccessDeniedException e) {
        ErrorDTO error = new ErrorDTO("У вас нет прав для выполнения этой операции", HttpStatus.FORBIDDEN.value());
        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }
}
