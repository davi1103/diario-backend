package com.upao.diariopersonalback.Repository;

import com.upao.diariopersonalback.Models.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.Date;
import java.util.List;

@Repository
public interface EntradaRepository extends JpaRepository<Entrada, Long> {

    // Método para buscar por texto
    @Query("SELECT e FROM Entrada e WHERE e.usuario.id = :usuarioId AND (" +
            "LOWER(e.titulo) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(e.descripcion) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Entrada> findByUsuarioIdAndTexto(Long usuarioId, String searchTerm);

    // Método para buscar por fecha
    @Query("SELECT e FROM Entrada e WHERE e.usuario.id = :usuarioId AND " +
            "DATE(e.fechaCreacion) = :fecha")
    List<Entrada> findByUsuarioIdAndFecha(Long usuarioId, Date fecha);
}