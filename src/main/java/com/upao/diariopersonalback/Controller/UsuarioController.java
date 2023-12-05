package com.upao.diariopersonalback.Controller;

import com.upao.diariopersonalback.DTO.UsuarioRegistroDTO;
import com.upao.diariopersonalback.DTO.UsuarioLoginDTO;
import com.upao.diariopersonalback.Models.Usuario;
import com.upao.diariopersonalback.Services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;
import java.util.Optional;

@CrossOrigin(origins = "https://mi-diario-personal.netlify.app", allowCredentials = "true")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/registro")
    public ResponseEntity<?> registrarUsuario(@RequestBody UsuarioRegistroDTO registroDTO) {
        try {
            Usuario usuarioRegistrado = usuarioService.registrarUsuario(registroDTO);
            // Omitir detalles sensibles como la contraseña antes de enviar la respuesta
            usuarioRegistrado.setContrasena(null);
            return ResponseEntity.ok().body("Usuario Regisrado correctamente, ahora ya puede iniciar sesión");
        } catch (IllegalStateException e) {
            // Devolver un mensaje de error si el correo ya está registrado
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> iniciarSesion(@RequestBody UsuarioLoginDTO loginDTO, HttpSession session) {
        String loginResponse = usuarioService.iniciarSesion(loginDTO);

        if ("Inicio de sesión exitoso".equals(loginResponse)) {
            Optional<Usuario> usuario = usuarioService.findByCorreo(loginDTO.getCorreo());
            if (usuario.isPresent()) {
                // Aquí es donde se almacena el ID del usuario en la sesión
                session.setAttribute("usuarioId", usuario.get().getId());
                return ResponseEntity.ok().body("Inicio de sesión exitoso");
            } else {
                // En caso de que no se encuentre el usuario por alguna razón, devuelve un error
                return ResponseEntity.status(401).body("Correo no encontrado");
            }
        } else {
            // Devuelve una respuesta diferente dependiendo del error
            return ResponseEntity.status(401).body(loginResponse);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> cerrarSesion(HttpSession session) {
        // Invalida la sesión actual para cerrar la sesión del usuario
        session.invalidate();
        System.out.println("Sesion terminada");
        return ResponseEntity.ok().body("Sesión cerrada con éxito");
    }

    @GetMapping("/session")
    public ResponseEntity<?> verificarSesion(HttpSession session) {
        if (session.getAttribute("usuarioId") != null) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
