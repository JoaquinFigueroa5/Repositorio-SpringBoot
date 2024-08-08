package com.joaquinfigueroa.webapp.biblioteca.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.joaquinfigueroa.webapp.biblioteca.model.Empleado;
import com.joaquinfigueroa.webapp.biblioteca.service.EmpleadoService;

@Controller
@RestController
@RequestMapping(value = "")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/empleados")
    public ResponseEntity<List<Empleado>> listarEmpleado(){
        try {
            return ResponseEntity.ok(empleadoService.listarEmpleado());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @GetMapping("/empleado")
    public ResponseEntity<Empleado> buscarEmpleado(@RequestParam Long id){
        try {
            return ResponseEntity.ok(empleadoService.buscarEmpleado(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/empleado")
    public ResponseEntity<Map<String, String>> agregarEmpleado(@RequestBody Empleado empleado){
        Map<String, String> response = new HashMap<>();
        try {
            if(empleadoService.guardarEmpleado(empleado);){
                response.put("Message", "Empleado creado con exito!");
                return ResponseEntity.ok(response);
            }else{
                response.put("message", "Dpi duplicado!");
                return ResponseEntity.badRequest().body(response);
            }
        } catch (Exception e) {
            response.put("message", "Error");
            response.put("err", "hubo un error al crear el empleado!");
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/empleado")
    public ResponseEntity<Map<String, String>> editarEmpleado(@RequestBody Long id, @RequestBody Empleado empleadoNuevo){
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleado(id);
            empleado.setNombre(empleadoNuevo.getNombre());
            empleado.setApellido(empleadoNuevo.getApellido());
            empleado.setTelefono(empleadoNuevo.getTelefono());
            empleado.setDireccion(empleadoNuevo.getDireccion());
            empleado.setDpi(empleadoNuevo.getDpi());
            empleadoService.guardarEmpleado(empleado);
            response.put("Message", "El empleado se ha editado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "el empleado no se pudo editar!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/empleado")
    public ResponseEntity<Map<String, String>> eliminarEmpleado(@RequestBody Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Empleado empleado = empleadoService.buscarEmpleado(id);
            empleadoService.eliminarEmpleado(empleado);
            response.put("message", "empleado eliminado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "el empleado no se pudo eliminar!");
            return ResponseEntity.badRequest().body(response);
        }
    }

}