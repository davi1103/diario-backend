package com.upao.diariopersonalback.Services;

import com.upao.diariopersonalback.DTO.UsuarioRegistroDTO;
import com.upao.diariopersonalback.DTO.UsuarioLoginDTO;
import com.upao.diariopersonalback.Models.Usuario;
import com.upao.diariopersonalback.Repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public Usuario registrarUsuario(UsuarioRegistroDTO registroDTO) {
        Optional<Usuario> usuarioExistente = usuarioRepository.findByCorreo(registroDTO.getCorreo());
        if (usuarioExistente.isPresent()) {
            throw new IllegalStateException("El correo ya está registrado.");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(registroDTO.getNombre());
        usuario.setCorreo(registroDTO.getCorreo());
        usuario.setContrasena(registroDTO.getContrasena()); // En un caso real, aquí deberías hashear la contraseña.
        return usuarioRepository.save(usuario);
    }

    public String iniciarSesion(UsuarioLoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByCorreo(loginDTO.getCorreo());

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();
            if (usuario.getContrasena().equals(loginDTO.getContrasena())) {
                return "Inicio de sesión exitoso";
            } else {
                return "Contraseña incorrecta";
            }
        } else {
            // En este punto, el correo es incorrecto.
            // Ahora, para comprobar si la contraseña también es incorrecta, debemos buscar si existe algún usuario con esa contraseña.
            // Esto NO es algo que normalmente haríamos por razones de seguridad, pero lo incluyo aquí para cumplir con el requisito.
            // En un escenario real, simplemente retornaríamos "Verificar el correo" sin verificar la contraseña.
            boolean contrasenaValida = usuarioRepository.findAll().stream()
                    .anyMatch(u -> u.getContrasena().equals(loginDTO.getContrasena()));
            if (contrasenaValida) {
                return "Verificar el correo";
            } else {
                return "Credenciales incorrectas";
            }
        }
    }
    // Método para buscar un usuario por correo

    public Optional<Usuario> findByCorreo(String correo) {
        return usuarioRepository.findByCorreo(correo);
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No se encontró un usuario con el ID: " + id));
    }
}