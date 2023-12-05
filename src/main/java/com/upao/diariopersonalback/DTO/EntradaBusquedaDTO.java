package com.upao.diariopersonalback.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
@Getter
@Setter
public class EntradaBusquedaDTO {
    private String titulo;
    private Date fecha;
    private String descripcion;
    private String imagen;
}