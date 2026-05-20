package com.example.bibliotecaapi.service.impl;

import com.example.bibliotecaapi.dto.PrestamoRequest;
import com.example.bibliotecaapi.dto.PrestamoResponse;
import com.example.bibliotecaapi.model.Prestamo;
import com.example.bibliotecaapi.model.Ejemplar; // Tu clase entidad Ejemplar
import com.example.bibliotecaapi.repository.PrestamoRepository;
import com.example.bibliotecaapi.repository.EjemplarRepository; // Tu repositorio de Ejemplar
import com.example.bibliotecaapi.service.PrestamoService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PrestamoServiceImpl implements PrestamoService {

    private final PrestamoRepository prestamoRepository;
    private final EjemplarRepository ejemplarRepository;

    // Inyección tradicional por constructor de ambos repositorios
    public PrestamoServiceImpl(PrestamoRepository prestamoRepository, EjemplarRepository ejemplarRepository) {
        this.prestamoRepository = prestamoRepository;
        this.ejemplarRepository = ejemplarRepository;
    }

    @Override
    public PrestamoResponse crearPrestamo(PrestamoRequest request) {
        // 1. Buscar si el ejemplar existe en la base de datos
        Ejemplar ejemplar = ejemplarRepository.findById(request.getEjemplarId())
                .orElseThrow(() -> new RuntimeException("Ejemplar no encontrado con ID: " + request.getEjemplarId()));

        // 2. VERIFICACIÓN: Comprobar que el estado sea estrictamente "DISPONIBLE"
        if (!"DISPONIBLE".equalsIgnoreCase(ejemplar.getEstado())) {
            throw new RuntimeException("No se puede realizar el préstamo. El ejemplar no está DISPONIBLE (Estado actual: " + ejemplar.getEstado() + ")");
        }

        // 3. Cambiar el estado del ejemplar a "PRESTADO" y guardar el cambio
        ejemplar.setEstado("PRESTADO");
        ejemplarRepository.save(ejemplar);

        // 4. Construir la entidad Prestamo calculando las fechas correspondientes
        Prestamo prestamo = new Prestamo();
        prestamo.setUsuarioId(request.getUsuarioId());
        prestamo.setEjemplarId(request.getEjemplarId());
        prestamo.setFechaPrestamo(LocalDate.now()); // Fecha de hoy
        prestamo.setFechaDevolucionEsperada(LocalDate.now().plusDays(request.getDiasPrestamo())); // Hoy + N días
        prestamo.setEstado("ACTIVO");

        // 5. Guardar el préstamo en MongoDB
        Prestamo prestamoGuardado = prestamoRepository.save(prestamo);

        return mapToResponse(prestamoGuardado);
    }

    @Override
    public PrestamoResponse registrarDevolucion(String prestamoId) {
        // 1. Buscar que el préstamo exista
        Prestamo prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new RuntimeException("Préstamo no encontrado con ID: " + prestamoId));

        // Validar que no haya sido devuelto antes
        if ("DEVUELTO".equalsIgnoreCase(prestamo.getEstado())) {
            throw new RuntimeException("Este préstamo ya fue devuelto anteriormente.");
        }

        // 2. Buscar el ejemplar asociado a ese préstamo
        Ejemplar ejemplar = ejemplarRepository.findById(prestamo.getEjemplarId())
                .orElseThrow(() -> new RuntimeException("Ejemplar asociado al préstamo no encontrado."));

        // 3. REGISTRAR DEVOLUCIÓN: Cambiar el estado del ejemplar a "DISPONIBLE" nuevamente
        ejemplar.setEstado("DISPONIBLE");
        ejemplarRepository.save(ejemplar);

        // 4. Cambiar el estado del registro de préstamo a "DEVUELTO"
        prestamo.setEstado("DEVUELTO");
        Prestamo prestamoActualizado = prestamoRepository.save(prestamo);

        return mapToResponse(prestamoActualizado);
    }

    // Método auxiliar de conversión manual tradicional
    private PrestamoResponse mapToResponse(Prestamo prestamo) {
        return new PrestamoResponse(
                prestamo.getId(),
                prestamo.getUsuarioId(),
                prestamo.getEjemplarId(),
                prestamo.getFechaPrestamo(),
                prestamo.getFechaDevolucionEsperada(),
                prestamo.getEstado()
        );
    }
}