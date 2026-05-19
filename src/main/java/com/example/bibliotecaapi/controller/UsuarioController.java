package com.example.bibliotecaapi.controller;

import com.example.bibliotecaapi.dto.UsuarioRequest;
import com.example.bibliotecaapi.dto.UsuarioResponse;
import com.example.bibliotecaapi.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Endpoint para crear/guardar un usuario

    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody UsuarioRequest usuarioRequest) {
        UsuarioResponse nuevoUsuario = usuarioService.save(usuarioRequest);

        return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioResponse>> findAll() {
        List<UsuarioResponse> usuarios = usuarioService.findAll();

        // Devolvemos la lista junto con un estado HTTP 200 OK
        return new ResponseEntity<>(usuarios, HttpStatus.OK);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable String id){
        usuarioService.deleteById(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
