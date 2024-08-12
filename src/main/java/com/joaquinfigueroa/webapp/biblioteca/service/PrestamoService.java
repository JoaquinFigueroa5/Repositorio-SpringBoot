package com.joaquinfigueroa.webapp.biblioteca.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaquinfigueroa.webapp.biblioteca.model.Libro;
import com.joaquinfigueroa.webapp.biblioteca.model.Prestamo;
import com.joaquinfigueroa.webapp.biblioteca.repository.PrestamoRepository;

@Service
public class PrestamoService implements IPrestamoService {

    @Autowired
    PrestamoRepository prestamoRepository;

    @Override
    public List<Prestamo> listarPrestamos() {
        return prestamoRepository.findAll();
    }

    @Override
    public Prestamo buscarPrestamoPorId(Long id) {
        return prestamoRepository.findById(id).orElse(null);
    }

    @Override
    public Boolean guardarPrestamo(Prestamo prestamo) {
        if(!verificarPrestamoActivo(prestamo)){
            prestamoRepository.save(prestamo);
            return true;
        }else{
            return false;
        }
        
    }

    @Override
    public void eliminarPrestamo(Prestamo prestamo) {
        prestamoRepository.delete(prestamo);
    }

    @Override
    public Boolean verificarPrestamoActivo(Prestamo prestamoActivo) {
        List<Prestamo> prestamos = listarPrestamos();
        Boolean flag = false;
        for (Prestamo prestamo : prestamos) {
            if(prestamoActivo.getVigencia().equals(prestamo.getVigencia()) && !prestamoActivo.getId().equals(prestamo.getId())){
                flag = true;
            }
        }

        return flag;
    }


}
