package com.upao.diariopersonalback.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class EntradaCreacionDTO {
    private String titulo;
    private String descripcion;
    private MultipartFile imagen;
    // Lombok se encarga de los getters y setters
}