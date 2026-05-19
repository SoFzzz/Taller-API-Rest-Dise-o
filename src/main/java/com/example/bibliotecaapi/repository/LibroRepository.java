package com.example.bibliotecaapi.repository;

import com.example.bibliotecaapi.model.Libro;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends MongoRepository<Libro, String> {

}
