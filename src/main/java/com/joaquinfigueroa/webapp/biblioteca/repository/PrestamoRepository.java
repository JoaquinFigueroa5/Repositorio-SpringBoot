package com.joaquinfigueroa.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaquinfigueroa.webapp.biblioteca.model.Prestamo;

public interface PrestamoRepository extends JpaRepository<Prestamo, Long>{

}
