package com.example.bibliotecaapi.repository;

import com.example.bibliotecaapi.model.Ejemplar;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EjemplarRepository extends MongoRepository<Ejemplar, String> {
}
