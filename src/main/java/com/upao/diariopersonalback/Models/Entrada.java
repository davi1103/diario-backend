package com.upao.diariopersonalback.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Entity
@Table(name = "entrada")
@Getter
@Setter
public class Entrada {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String titulo;

    @Column(nullable = false, length = 4000)
    private String descripcion;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    private Date fechaCreacion = new Date(); // Fecha de creación por defecto

    @Lob
    @Column(name = "imagen", columnDefinition = "MEDIUMBLOB")
    private byte[] imagen;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario; // Relación con la entidad Usuario

    // Lombok se encarga de los constructores, getters y setters
}