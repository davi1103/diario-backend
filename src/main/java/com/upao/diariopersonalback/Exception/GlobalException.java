package com.upao.diariopersonalback.Exception;

import com.upao.diariopersonalback.Exception.MiExcepcionDeAutenticacion;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalException {

    @ExceptionHandler(MiExcepcionDeAutenticacion.class)
    public ResponseEntity<String> handleUsuarioNoLogueadoException(MiExcepcionDeAutenticacion ex) {
        // Aquí defines la respuesta para la excepción UsuarioNoLogueadoException
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // 401 Unauthorized
                .body(ex.getMessage());
    }

}