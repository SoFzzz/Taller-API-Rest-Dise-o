package com.example.bibliotecaapi.controller;

import com.example.bibliotecaapi.dto.EjemplarRequest;
import com.example.bibliotecaapi.dto.EjemplarResponse;
import com.example.bibliotecaapi.service.EjemplarService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ejemplares")
public class EjemplarController {

    private final EjemplarService ejemplarService;

    public EjemplarController(EjemplarService ejemplarService) {
        this.ejemplarService = ejemplarService;
    }

    @PostMapping
    public ResponseEntity<EjemplarResponse> crearEjemplar(@RequestBody EjemplarRequest request) {
        EjemplarResponse response = ejemplarService.crearEjemplar(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EjemplarResponse> consultarEjemplar(@PathVariable String id) {
        EjemplarResponse response = ejemplarService.consultarEjemplar(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<EjemplarResponse>> listarEjemplares() {
        List<EjemplarResponse> lista = ejemplarService.listarEjemplares();
        return new ResponseEntity<>(lista, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarEjemplar(@PathVariable String id) {
        ejemplarService.eliminarEjemplar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}