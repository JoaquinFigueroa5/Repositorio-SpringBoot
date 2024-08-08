package com.joaquinfigueroa.webapp.biblioteca.service;

import java.util.List;

import com.joaquinfigueroa.webapp.biblioteca.model.Empleado;

public interface IEmpleadoService {
    public List<Empleado> listarEmpleado();

    public Boolean guardarEmpleado(Empleado empleado);

    public Empleado buscarEmpleado(Long id);

    public void eliminarEmpleado(Empleado empleado);

    public Boolean verificarDpiDuplicado(Empleado empleadoNuevo):
}