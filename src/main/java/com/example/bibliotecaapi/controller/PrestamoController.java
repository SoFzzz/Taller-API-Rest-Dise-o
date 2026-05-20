package com.example.bibliotecaapi.controller;

import com.example.bibliotecaapi.dto.PrestamoRequest;
import com.example.bibliotecaapi.dto.PrestamoResponse;
import com.example.bibliotecaapi.service.PrestamoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prestamos")
public class PrestamoController {

    private final PrestamoService prestamoService;

    public PrestamoController(PrestamoService prestamoService) {
        this.prestamoService = prestamoService;
    }


    @PostMapping
    public ResponseEntity<PrestamoResponse> crearPrestamo(@RequestBody PrestamoRequest request) {
        PrestamoResponse response = prestamoService.crearPrestamo(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PutMapping("/{id}/devolucion")
    public ResponseEntity<PrestamoResponse> registrarDevolucion(@PathVariable String id) {
        PrestamoResponse response = prestamoService.registrarDevolucion(id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}