package com.example.bibliotecaapi.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class PrestamoRequest {
    private String usuarioId;
    private String ejemplarId;
    private int diasPrestamo; // Cantidad de días que se prestará (ej. 7 o 15 días) para calcular la fecha esperada.

}