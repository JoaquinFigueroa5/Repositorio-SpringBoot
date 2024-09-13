package com.joaquinfigueroa.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaquinfigueroa.webapp.biblioteca.model.Cliente;
import com.joaquinfigueroa.webapp.biblioteca.service.ClienteService;
import com.joaquinfigueroa.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class clientesFXController implements Initializable{

    @FXML
    TextField tfDPI, tfNombre, tfApellido, tfTelefono, tfNIT;

    @FXML
    TextArea taDireccion;

    @FXML
    Button btnGuardar, btnLimpiar, btnEliminar, btnRegresar;

    @FXML
    TableView tblClientes;

    @FXML
    TableColumn colDPI, colNombre, colApellido, colTelefono, colDireccion, colNIT;

    @Autowired
    ClienteService clienteService;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfDPI.getText().equals(tfDPI.getText())){
                agregarCliente();
            }else{
                editarCliente();
            }
        }else if(event.getSource() == btnLimpiar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarCliente();
        }
    }

    public void cargarDatos(){
        tblClientes.setItems(listarClientes());
        colDPI.setCellValueFactory(new PropertyValueFactory<Cliente, Long>("dpi"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        colNIT.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarClientes());
    }

    public void cargarFormEditar(){
        Cliente cliente = (Cliente)tblClientes.getSelectionModel().getSelectedItem();
        if(cliente != null){
            tfDPI.setText(Long.toString(cliente.getDpi()));
            tfNombre.setText(cliente.getNombre());
            tfApellido.setText(cliente.getApellido());
            tfTelefono.setText(cliente.getTelefono());
            taDireccion.setText(cliente.getDireccion());
            tfNIT.setText(cliente.getNit());
        }
    }

    public void LimpiarFormEditar(){
        tfDPI.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        taDireccion.clear();
        tfNIT.clear();
    }

    public void agregarCliente(){
        Cliente cliente = new Cliente();
        cliente.setDpi(Long.parseLong(tfDPI.getText()));
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        cliente.setDireccion(taDireccion.getText());
        cliente.setNit(tfNIT.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void editarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDPI.getText()));
        cliente.setNombre(tfNombre.getText());
        cliente.setApellido(tfApellido.getText());
        cliente.setTelefono(tfTelefono.getText());
        cliente.setDireccion(taDireccion.getText());
        cliente.setNit(tfNIT.getText());
        clienteService.guardarCliente(cliente);
        cargarDatos();
    }

    public void eliminarCliente(){
        Cliente cliente = clienteService.buscarClientePorId(Long.parseLong(tfDPI.getText()));
        clienteService.eliminarCliente(cliente);
        cargarDatos();
    }

}


