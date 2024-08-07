package com.joaquinfigueroa.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaquinfigueroa.webapp.biblioteca.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{

}