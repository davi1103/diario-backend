package com.upao.diariopersonalback.Services;

import com.upao.diariopersonalback.DTO.EntradaBusquedaDTO;
import com.upao.diariopersonalback.Models.Entrada;
import com.upao.diariopersonalback.Models.Usuario;
import com.upao.diariopersonalback.Repository.EntradaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EntradaService {

    private final EntradaRepository entradaRepository;

    @Autowired
    public EntradaService(EntradaRepository entradaRepository) {
        this.entradaRepository = entradaRepository;
    }

    public Entrada crearEntrada(String titulo, String descripcion, MultipartFile imagen, Usuario usuario) throws IOException {
        Entrada entrada = new Entrada();
        entrada.setTitulo(titulo);
        entrada.setDescripcion(descripcion);
        entrada.setImagen(imagen.getBytes()); // Convertir MultipartFile a byte[]
        entrada.setUsuario(usuario);
        entrada.setFechaCreacion(new Date()); // O establecer la fecha de creación aquí si no está en el modelo
        return entradaRepository.save(entrada);
    }

    public List<EntradaBusquedaDTO> buscarPorTexto(Long usuarioId, String searchTerm) {
        List<Entrada> entradas = entradaRepository.findByUsuarioIdAndTexto(usuarioId, searchTerm);
        return entradas.stream().map(this::convertirAEntradaBusquedaDTO).collect(Collectors.toList());
    }

    public List<EntradaBusquedaDTO> buscarPorFecha(Long usuarioId, Date fecha) {
        List<Entrada> entradas = entradaRepository.findByUsuarioIdAndFecha(usuarioId, fecha);
        return entradas.stream().map(this::convertirAEntradaBusquedaDTO).collect(Collectors.toList());
    }

    private EntradaBusquedaDTO convertirAEntradaBusquedaDTO(Entrada entrada) {
        EntradaBusquedaDTO dto = new EntradaBusquedaDTO();
        dto.setTitulo(entrada.getTitulo());
        dto.setDescripcion(entrada.getDescripcion());
        dto.setFecha(entrada.getFechaCreacion());
        if (entrada.getImagen() != null) {
            dto.setImagen(Base64.getEncoder().encodeToString(entrada.getImagen()));
        }
        return dto;
    }

    // Aquí debes incluir los métodos del repositorio para buscar por texto y fecha
    // y cualquier otro método de utilidad para el servicio que necesites.
}