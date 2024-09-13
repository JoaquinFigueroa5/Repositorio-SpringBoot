package com.joaquinfigueroa.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaquinfigueroa.webapp.biblioteca.model.Cliente;
import com.joaquinfigueroa.webapp.biblioteca.model.Empleado;
import com.joaquinfigueroa.webapp.biblioteca.model.Libro;
import com.joaquinfigueroa.webapp.biblioteca.model.Prestamo;
import com.joaquinfigueroa.webapp.biblioteca.service.ClienteService;
import com.joaquinfigueroa.webapp.biblioteca.service.EmpleadoService;
import com.joaquinfigueroa.webapp.biblioteca.service.LibroService;
import com.joaquinfigueroa.webapp.biblioteca.service.PrestamoService;
import com.joaquinfigueroa.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class prestamoFXController implements Initializable{
    
    @FXML
    TextField tfId;

    @FXML
    DatePicker dtPrestamo, dtDevolucion;

    @FXML
    Button btnGuardar, btnEliminar, btnLimpiar, btnRegresar;

    @FXML
    ComboBox cmbVigencia, cmbEmpleado, cmbCliente, cmbLibro;

    @FXML
    TableView tblPrestamos;

    @FXML
    TableColumn colId, colPrestamo, colDevolucion, colVigencia, colEmpleado, colCliente, colLibros;

    @Autowired
    PrestamoService prestamoService;

    @Autowired
    EmpleadoService empleadoService;

    @Autowired
    ClienteService clienteService;

    @Autowired
    LibroService libroService;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cmbVigencia.setItems(listarVigencia());
        cmbEmpleado.setItems(listarEmpleados());
        cmbCliente.setItems(listarClientes());
        cmbLibro.setItems(listarLibros());
    }

     public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarPrestamo();
            }else{
                editarPrestamo();
            }
        }else if(event.getSource() == btnLimpiar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarPrestamo();
        }
    }

    public void cargarDatos(){
        tblPrestamos.setItems(listarPrestamos());
        colId.setCellValueFactory(new PropertyValueFactory<Prestamo, Long>("id"));
        colPrestamo.setCellValueFactory(new PropertyValueFactory<Prestamo, LocalDate>("fechaDePrestamo"));
        colDevolucion.setCellValueFactory(new PropertyValueFactory<Prestamo, LocalDate>("fechaDeDevolucion"));
        colVigencia.setCellValueFactory(new PropertyValueFactory<Prestamo, Boolean>("vigencia"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Prestamo, Empleado>("empleado"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Prestamo, Cliente>("cliente"));
        colLibros.setCellValueFactory(new PropertyValueFactory<Prestamo, Libro>("libros"));
        
    }

    public ObservableList<Prestamo> listarPrestamos(){
        return FXCollections.observableList(prestamoService.listarPrestamos());
    }

    public ObservableList<Empleado> listarEmpleados(){
        return FXCollections.observableList(empleadoService.listarEmpleado());
    }

    public ObservableList<Cliente> listarClientes(){
        return FXCollections.observableList(clienteService.listarClientes());
    }

    public ObservableList<Libro> listarLibros(){
        return FXCollections.observableList(libroService.listarLibros());
    }

    public ObservableList<String> listarVigencia(){
        return FXCollections.observableArrayList("Vigente", "No vigente");
    }

    public void cargarFormEditar(){
        Prestamo prestamo = (Prestamo)tblPrestamos.getSelectionModel().getSelectedItem();
        if(prestamo != null){
            tfId.setText(Long.toString(prestamo.getId()));
            dtPrestamo.setValue(prestamo.getFechaDePrestamo());
            dtDevolucion.setValue(prestamo.getFechaDeDevolucion());
            cmbVigencia.getSelectionModel().select(prestamo.getVigencia() ? "Vigente" : "No vigente");
            cmbEmpleado.getSelectionModel().select(prestamo.getEmpleado());
            cmbCliente.getSelectionModel().select(prestamo.getCliente());
            cmbLibro.getSelectionModel().select(prestamo.getLibros());
            
        }
    }

    public void LimpiarFormEditar(){
        tfId.clear();
        dtPrestamo.setValue(null);
        dtDevolucion.setValue(null);
        cmbVigencia.getSelectionModel().clearSelection();
        cmbEmpleado.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbLibro.getSelectionModel().clearSelection();
    }

    public void agregarPrestamo(){
        Prestamo prestamo = new Prestamo();
        prestamo.setFechaDePrestamo(dtPrestamo.getValue());
        prestamo.setFechaDeDevolucion(dtDevolucion.getValue());
        prestamo.setVigencia(cmbVigencia.getSelectionModel().getSelectedItem().equals("vigente"));
        prestamo.setEmpleado((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem());
        prestamo.setCliente((Cliente)cmbCliente.getSelectionModel().getSelectedItem());
        prestamo.setLibros((List<Libro>)cmbLibro.getSelectionModel().getSelectedItem());
        prestamoService.guardarPrestamo(prestamo);
        cargarDatos();
    }

    public void editarPrestamo(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamo.setFechaDePrestamo(dtPrestamo.getValue());
        prestamo.setFechaDeDevolucion(dtDevolucion.getValue());
        prestamo.setVigencia(cmbVigencia.getSelectionModel().getSelectedItem().equals("vigente"));
        prestamo.setEmpleado((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem());
        prestamo.setCliente((Cliente)cmbCliente.getSelectionModel().getSelectedItem());
        prestamo.setLibros((List<Libro>)cmbLibro.getSelectionModel().getSelectedItem());
        prestamoService.guardarPrestamo(prestamo);
        cargarDatos();
    }

    public void eliminarPrestamo(){
        Prestamo prestamo = prestamoService.buscarPrestamoPorId(Long.parseLong(tfId.getText()));
        prestamoService.eliminarPrestamo(prestamo);
        cargarDatos();
    }
}
