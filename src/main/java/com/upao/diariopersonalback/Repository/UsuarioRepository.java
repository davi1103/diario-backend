package com.upao.diariopersonalback.Repository;

import com.upao.diariopersonalback.Models.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    // Método para buscar un usuario por correo electrónico, devuelve un Optional de Usuario
    Optional<Usuario> findByCorreo(String correo);
}