package com.example.bibliotecaapi.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Document(collection = "ejemplares")
@NoArgsConstructor
@AllArgsConstructor
public class Ejemplar {

    @Id
    private String id;
    private String libroId;
    private String estado;

}