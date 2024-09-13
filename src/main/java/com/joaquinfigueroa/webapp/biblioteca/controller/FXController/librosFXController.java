package com.joaquinfigueroa.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaquinfigueroa.webapp.biblioteca.model.Categoria;
import com.joaquinfigueroa.webapp.biblioteca.model.Libro;
import com.joaquinfigueroa.webapp.biblioteca.service.CategoriaService;
import com.joaquinfigueroa.webapp.biblioteca.service.LibroService;
import com.joaquinfigueroa.webapp.biblioteca.system.Main;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import lombok.Setter;

@Component
public class librosFXController implements Initializable{

    @FXML
    TextField tfId, tfISBN, tfNombre, tfAutor, tfEditorial, tfNoEstanteria, tfCluster;

    @FXML
    TextArea taSinopsis;

    @FXML
    ComboBox cmbDisponibilidad, cmbCategoria;

    @FXML
    Button btnGuardar, btnEliminar, btnLimpiar, btnRegresar;

    @FXML 
    TableView tblLibros;

    @FXML
    TableColumn colId, colISBN, colNombre, colSinopsis, colAutor, colEditorial, colDispo, colEstanteria, colCluster, colCategoria;

    @Autowired
    LibroService libroService;

    @Autowired
    CategoriaService categoriaService;

    @Setter
    private Main stage;

    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
        cmbDisponibilidad.setItems(listarDisponibilidad());
        cmbCategoria.setItems(listarCategoria());
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarLibro();
            }else{
                editarLibro();
            }
        }else if(event.getSource() == btnLimpiar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarLibro();
        }
    }

    public void cargarDatos(){
        tblLibros.setItems(listarLibros());
        colId.setCellValueFactory(new PropertyValueFactory<Libro, Long>("id"));
        colISBN.setCellValueFactory(new PropertyValueFactory<Libro, String>("isbn"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Libro, String>("nombre"));
        colSinopsis.setCellValueFactory(new PropertyValueFactory<Libro, String>("sinopsis"));
        colAutor.setCellValueFactory(new PropertyValueFactory<Libro, String>("autor"));
        colEditorial.setCellValueFactory(new PropertyValueFactory<Libro, String>("editorial"));
        colDispo.setCellValueFactory(new PropertyValueFactory<Libro, Boolean>("disponibilidad"));
        colEstanteria.setCellValueFactory(new PropertyValueFactory<Libro, String>("numeroEstanteria"));
        colCluster.setCellValueFactory(new PropertyValueFactory<Libro, String>("cluster"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Libro, String>("categoria"));
    }

    public ObservableList<Libro> listarLibros(){
        return FXCollections.observableList(libroService.listarLibros());
    }

    public ObservableList<Categoria> listarCategoria(){
        return FXCollections.observableList(categoriaService.listarCategoria());
    }

    public ObservableList<String> listarDisponibilidad() {
        return FXCollections.observableArrayList("Disponible", "No disponible");
    }

    public void cargarFormEditar(){
        Libro libro = (Libro)tblLibros.getSelectionModel().getSelectedItem();
        if(libro != null){
            tfId.setText(Long.toString(libro.getId()));
            tfISBN.setText(libro.getIsbn());
            tfNombre.setText(libro.getNombre());
            taSinopsis.setText(libro.getSinopsis());
            tfAutor.setText(libro.getAutor());
            tfEditorial.setText(libro.getEditorial());
            cmbDisponibilidad.getSelectionModel().select(libro.getDisponibilidad() ? "Disponible" : "No disponible");
            tfNoEstanteria.setText(libro.getNumeroEstanteria());
            tfCluster.setText(libro.getCluster());
            cmbCategoria.getSelectionModel().select(libro.getCategoria());
        }
    }

    public void LimpiarFormEditar(){
        tfId.clear();
        tfISBN.clear();
        tfNombre.clear();
        taSinopsis.clear();
        tfAutor.clear();
        tfEditorial.clear();
        cmbDisponibilidad.getSelectionModel().clearSelection();
        tfNoEstanteria.clear();
        tfCluster.clear();
        cmbCategoria.getSelectionModel().clearSelection();
    }

    public void agregarLibro(){
        Libro libro = new Libro();
        libro.setIsbn(tfISBN.getText());
        libro.setNombre(tfNombre.getText());
        libro.setSinopsis(taSinopsis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());
        libro.setDisponibilidad(cmbDisponibilidad.getSelectionModel().getSelectedItem().equals("Disponible"));
        libro.setNumeroEstanteria(tfNoEstanteria.getText());
        libro.setCluster(tfCluster.getText());
        libro.setCategoria((Categoria)cmbCategoria.getSelectionModel().getSelectedItem());
        libroService.guardarLibro(libro);
        cargarDatos();
    }

    public void editarLibro(){
        Libro libro = libroService.buscarLibro(Long.parseLong(tfId.getText()));
        libro.setIsbn(tfISBN.getText());
        libro.setNombre(tfNombre.getText());
        libro.setSinopsis(taSinopsis.getText());
        libro.setAutor(tfAutor.getText());
        libro.setEditorial(tfEditorial.getText());
        libro.setDisponibilidad(cmbDisponibilidad.getSelectionModel().getSelectedItem().equals("Disponible"));
        libro.setNumeroEstanteria(tfNoEstanteria.getText());
        libro.setCluster(tfCluster.getText());
        libro.setCategoria((Categoria)cmbCategoria.getSelectionModel().getSelectedItem());
        libroService.guardarLibro(libro);
        cargarDatos();
    }

    public void eliminarLibro(){
        Libro libro = libroService.buscarLibro(Long.parseLong(tfId.getText()));
        libroService.eilimnaLibro(libro);
        cargarDatos();
    }
}
