package com.example.bibliotecaapi.service;

import com.example.bibliotecaapi.dto.LibroRequest;
import com.example.bibliotecaapi.dto.LibroResponse;
import java.util.List;

public interface LibroService {

    LibroResponse crearLibro(LibroRequest request);

    LibroResponse actualizarLibro(String id, LibroRequest request);

    void eliminarLibro(String id);

    LibroResponse consultarLibro(String id);

    List<LibroResponse> listarLibros();
}
