package com.joaquinfigueroa.webapp.biblioteca.service;

import java.util.List;

import com.joaquinfigueroa.webapp.biblioteca.model.Libro;

public interface ILibroService {
    public List<Libro> listarLibros();

    public Libro guardarLibro(Libro libro);

    public Libro buscarLibro(Long id);

    public void eilimnaLibro(Libro libro);
}