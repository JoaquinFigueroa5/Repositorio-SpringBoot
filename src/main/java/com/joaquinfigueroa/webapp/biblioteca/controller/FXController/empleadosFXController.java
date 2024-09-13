package com.joaquinfigueroa.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaquinfigueroa.webapp.biblioteca.model.Empleado;
import com.joaquinfigueroa.webapp.biblioteca.service.EmpleadoService;
import com.joaquinfigueroa.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class empleadosFXController implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfApellido, tfTelefono, tfDireccion, tfDPI;

    @FXML
    Button btnGuardar, btnEliminar, btnLimpiar, btnRegresar;

    @FXML
    TableView tblEmpleado;

    @FXML
    TableColumn colId, colNombre, colApellido, colTelefono, colDireccion, colDPI;

    @Autowired
    EmpleadoService empleadoService;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarEmpleado();
            }else{
                editarEmpleado();
            }
        }else if(event.getSource() == btnLimpiar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarEmpleado();
        }
    }

    public void cargarDatos(){
        tblEmpleado.setItems(listarEmpleado());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Empleado, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Empleado, String>("direccion"));
        colDPI.setCellValueFactory(new PropertyValueFactory<Empleado, String>("dpi"));
    }

    public ObservableList<Empleado> listarEmpleado(){
        return FXCollections.observableList(empleadoService.listarEmpleado());
    }

    public void cargarFormEditar(){
        Empleado empleado = (Empleado)tblEmpleado.getSelectionModel().getSelectedItem();
        if(empleado != null){
            tfId.setText(Long.toString(empleado.getId()));
            tfNombre.setText(empleado.getNombre());
            tfApellido.setText(empleado.getApellido());
            tfTelefono.setText(empleado.getTelefono());
            tfDireccion.setText(empleado.getDireccion());
            tfDPI.setText(empleado.getDpi());
        }
    }

    public void LimpiarFormEditar(){
        tfId.clear();
        tfNombre.clear();
        tfApellido.clear();
        tfTelefono.clear();
        tfDireccion.clear();
        tfDPI.clear();
    }

    public void agregarEmpleado(){
        Empleado empleado = new Empleado();
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDPI.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void editarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleado(Long.parseLong(tfId.getText()));
        empleado.setNombre(tfNombre.getText());
        empleado.setApellido(tfApellido.getText());
        empleado.setTelefono(tfTelefono.getText());
        empleado.setDireccion(tfDireccion.getText());
        empleado.setDpi(tfDPI.getText());
        empleadoService.guardarEmpleado(empleado);
        cargarDatos();
    }

    public void eliminarEmpleado(){
        Empleado empleado = empleadoService.buscarEmpleado(Long.parseLong(tfId.getText()));
        empleadoService.eliminarEmpleado(empleado);
        cargarDatos();
    }

}
