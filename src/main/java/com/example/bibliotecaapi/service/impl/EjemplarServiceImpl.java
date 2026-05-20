package com.example.bibliotecaapi.service.impl;

import com.example.bibliotecaapi.dto.EjemplarRequest;
import com.example.bibliotecaapi.dto.EjemplarResponse;
import com.example.bibliotecaapi.model.Ejemplar;
import com.example.bibliotecaapi.repository.EjemplarRepository;
import com.example.bibliotecaapi.repository.LibroRepository; // Opcional para validar existencia del libro
import com.example.bibliotecaapi.service.EjemplarService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EjemplarServiceImpl implements EjemplarService {

    private final EjemplarRepository ejemplarRepository;
    private final LibroRepository libroRepository;


    public EjemplarServiceImpl(EjemplarRepository ejemplarRepository, LibroRepository libroRepository) {
        this.ejemplarRepository = ejemplarRepository;
        this.libroRepository = libroRepository;
    }

    @Override
    public EjemplarResponse crearEjemplar(EjemplarRequest request) {

        if (!libroRepository.existsById(request.getLibroId())) {
            throw new RuntimeException("No se puede crear el ejemplar. El libro con ID " + request.getLibroId() + " no existe.");
        }


        Ejemplar ejemplar = new Ejemplar();
        ejemplar.setLibroId(request.getLibroId());

        ejemplar.setEstado(request.getEstado() != null ? request.getEstado().toUpperCase() : "DISPONIBLE");


        Ejemplar ejemplarGuardado = ejemplarRepository.save(ejemplar);

        return mapToResponse(ejemplarGuardado);
    }

    @Override
    public EjemplarResponse consultarEjemplar(String id) {
        Ejemplar ejemplar = ejemplarRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado con id: " + id));
        return mapToResponse(ejemplar);
    }

    @Override
    public List<EjemplarResponse> listarEjemplares() {
        return ejemplarRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void eliminarEjemplar(String id) {
        if (!ejemplarRepository.existsById(id)) {
            throw new RuntimeException("Ejemplar no encontrado con id: " + id);
        }
        ejemplarRepository.deleteById(id);
    }

    // ─────────────────────────────────────────────
    // Método auxiliar: convierte Ejemplar → EjemplarResponse
    // ─────────────────────────────────────────────
    private EjemplarResponse mapToResponse(Ejemplar ejemplar) {
        return new EjemplarResponse(
                ejemplar.getId(),
                ejemplar.getLibroId(),
                ejemplar.getEstado()
        );
    }
}
