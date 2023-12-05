    package com.upao.diariopersonalback.Controller;
    import com.upao.diariopersonalback.DTO.EntradaBusquedaDTO;
    import com.upao.diariopersonalback.Exception.MiExcepcionDeAutenticacion;
    import com.upao.diariopersonalback.Models.Entrada;
    import com.upao.diariopersonalback.Models.Usuario;
    import com.upao.diariopersonalback.Services.EntradaService;
    import com.upao.diariopersonalback.Services.UsuarioService;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.format.annotation.DateTimeFormat;
    import jakarta.servlet.http.HttpSession;
    import org.springframework.web.multipart.MultipartFile;

    import java.io.IOException;
    import java.util.Date;
    import java.util.List;

    @CrossOrigin(origins = "https://mi-diario-personal.netlify.app", allowCredentials = "true")
    @RestController
    @RequestMapping("/api/entradas")
    public class EntradaController {

        @Autowired
        private EntradaService entradaService;

        @Autowired
        private UsuarioService usuarioService;

        @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
        public ResponseEntity<?> crearEntrada(
                @RequestParam("titulo") String titulo,
                @RequestParam("descripcion") String descripcion,
                @RequestParam("imagen") MultipartFile imagen,
                HttpSession session) {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            if (usuarioId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Debe iniciar sesión para crear una entrada");
            }

            try {
                Usuario usuario = usuarioService.obtenerUsuarioPorId(usuarioId);
                Entrada nuevaEntrada = entradaService.crearEntrada(titulo, descripcion, imagen, usuario);
                return ResponseEntity.ok(nuevaEntrada);
            } catch (IOException e) {
                e.printStackTrace();
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al procesar la imagen");
            }
        }

        @GetMapping("/buscarTexto")
        public ResponseEntity<List<EntradaBusquedaDTO>> buscarPorTexto(@RequestParam String searchTerm, HttpSession session) {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            if (usuarioId == null) {
                throw new MiExcepcionDeAutenticacion("El usuario necesita estar logueado para realizar esta acción.");
            }
            List<EntradaBusquedaDTO> entradas = entradaService.buscarPorTexto(usuarioId, searchTerm);
            return ResponseEntity.ok(entradas);
        }

        @GetMapping("/buscarFecha")
        public ResponseEntity<List<EntradaBusquedaDTO>> buscarPorFecha(
                @RequestParam @DateTimeFormat(pattern="yyyy-MM-dd") Date fecha,
                HttpSession session) {
            Long usuarioId = (Long) session.getAttribute("usuarioId");
            if (usuarioId == null) {
                throw new MiExcepcionDeAutenticacion("El usuario necesita estar logueado para realizar esta acción.");
            }
            List<EntradaBusquedaDTO> entradas = entradaService.buscarPorFecha(usuarioId, fecha);
            return ResponseEntity.ok(entradas);
        }

        // Agrega aquí otros métodos de tu controlador si son necesarios

        // Excepciones y otros manejadores pueden ser añadidos aquí
    }