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

import com.joaquinfigueroa.webapp.biblioteca.model.Cliente;
import com.joaquinfigueroa.webapp.biblioteca.service.ClienteService;

@Controller
@RestController
@RequestMapping(value = "cliente")
public class ClienteController {

    @Autowired
    ClienteService clienteService;

    @GetMapping("/clientes")
    public List<Cliente> listarClientes(){
        return clienteService.listarClientes();
    }

    @GetMapping("/cliente")
    public ResponseEntity<Cliente> buscarClientePorId(@RequestParam Long dpi){
        try {
            return ResponseEntity.ok(clienteService.buscarClientePorId(dpi));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/cliente")
    public ResponseEntity<Map<String, Boolean>> agregarCategoria(@RequestBody Cliente cliente){
        Map<String, Boolean> response = new HashMap<>();
        try {
            clienteService.guardarCliente(cliente);
            response.put("Se agrego con exito!", Boolean.TRUE);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("No se pudo agregar!", Boolean.FALSE);
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PutMapping("/cliente")
    public ResponseEntity<Map<String, String>> editarCliente(@RequestParam Long dpi, @RequestBody Cliente clienteNuevo){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(dpi);
            cliente.setNombre(clienteNuevo.getNombre());
            cliente.setApellido(clienteNuevo.getApellido());
            cliente.setTelefono(clienteNuevo.getTelefono());
            cliente.setDireccion(clienteNuevo.getDireccion());
            cliente.setNit(clienteNuevo.getNit());
            clienteService.guardarCliente(cliente);
            response.put("message", "El cliente se ha editado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El cliente no se pudo editar!");
            return ResponseEntity.badRequest().body(response);
        }
    }

    @DeleteMapping("/cliente")
    public ResponseEntity<Map<String, String>> eliminarCliente(@RequestParam Long dpi){
        Map<String, String> response = new HashMap<>();
        try {
            Cliente cliente = clienteService.buscarClientePorId(dpi);
            clienteService.eliminarCliente(cliente);
            response.put("message", "Cliente eliminado con exito!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.put("message", "El cliente no se pudo eliminar!");
            return ResponseEntity.badRequest().body(response);
        }
    }
}
