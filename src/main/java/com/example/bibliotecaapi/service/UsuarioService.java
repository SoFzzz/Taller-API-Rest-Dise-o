package com.example.bibliotecaapi.service;

import com.example.bibliotecaapi.dto.UsuarioRequest;
import com.example.bibliotecaapi.dto.UsuarioResponse;
import java.util.List;

public interface UsuarioService {
    UsuarioResponse save(UsuarioRequest usuarioRequest);
    List<UsuarioResponse> findAll();
    void deleteById(String id);
}
