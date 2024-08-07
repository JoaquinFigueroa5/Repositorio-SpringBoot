package com.joaquinfigueroa.webapp.biblioteca.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.joaquinfigueroa.webapp.biblioteca.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
