package com.joaquinfigueroa.webapp.biblioteca.service;


import java.util.List;
import com.joaquinfigueroa.webapp.biblioteca.model.*;

public interface ICategoriaService {
    public List<Categoria> listarCategoria();
    
    public Categoria buscarCategoriaPorId(Long id);
    
    public Boolean guardarCategoria(Categoria categoria);
    
    public void eliminarCategoria(Categoria categoria);

    public Boolean verificarCategoriaDuplicado(Categoria categoriaNueva);
}
