package com.example.bibliotecaapi.service;

import com.example.bibliotecaapi.dto.EjemplarRequest;
import com.example.bibliotecaapi.dto.EjemplarResponse;
import java.util.List;

public interface EjemplarService {
    EjemplarResponse crearEjemplar(EjemplarRequest request);
    EjemplarResponse consultarEjemplar(String id);
    List<EjemplarResponse> listarEjemplares();
    void eliminarEjemplar(String id);
}