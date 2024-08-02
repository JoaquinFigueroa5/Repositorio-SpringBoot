package com.joaquinfigueroa.webapp.biblioteca.repository;

import com.joaquinfigueroa.webapp.biblioteca.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    
}
