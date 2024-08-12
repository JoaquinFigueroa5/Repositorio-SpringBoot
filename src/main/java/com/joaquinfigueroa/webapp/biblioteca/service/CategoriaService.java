package com.joaquinfigueroa.webapp.biblioteca.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquinfigueroa.webapp.biblioteca.model.Categoria;
import com.joaquinfigueroa.webapp.biblioteca.repository.CategoriaRepository;

@Service
public class CategoriaService implements ICategoriaService{


    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    public List<Categoria> listarCategoria() {
        return categoriaRepository.findAll();
    }

    @Override
    public Categoria buscarCategoriaPorId(Long id) {
        return categoriaRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarCategoria(Categoria categoria) {
        if (!verificarCategoriaDuplicado(categoria)) {
            categoriaRepository.save(categoria);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void eliminarCategoria(Categoria categoria) {
        categoriaRepository.delete(categoria);
    }

    @Override
    public Boolean verificarCategoriaDuplicado(Categoria categoriaNueva) {
        List<Categoria> categorias = listarCategoria();
        Boolean flag = false;
        for (Categoria categoria : categorias) {
            if(categoriaNueva.getNombreCategoria().trim().equalsIgnoreCase(categoria.getNombreCategoria().trim()) && !categoriaNueva.getId().equals(categoria.getId())){
                return true;
            }
        }
        
        return flag;
    }

}
