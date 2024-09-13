package com.joaquinfigueroa.webapp.biblioteca.controller.FXController;

import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.joaquinfigueroa.webapp.biblioteca.model.Categoria;
import com.joaquinfigueroa.webapp.biblioteca.service.CategoriaService;
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
public class categoriasFXController implements Initializable{

    @FXML
    TextField tfId, tfNombre, tfBuscar;

    @FXML
    Button btnGuardar, btnLimpiar, btnRegresar, btnEliminar, btnBuscar;

    @FXML
    TableView tblCategoria;

    @FXML
    TableColumn colId, colNombre;

    @Setter
    private Main stage;

    @Autowired
    CategoriaService categoriaService;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
        cargarDatos();
    }

    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(tfId.getText().isBlank()){
                agregarCategoria();
            }else{
                editarCategoria();
            }
        }else if(event.getSource() == btnLimpiar){
            LimpiarFormEditar();
        }else if(event.getSource() == btnRegresar){
            stage.indexView();
        }else if(event.getSource() == btnEliminar){
            eliminarCategoria();
        }else if(event.getSource() == btnBuscar){
            buscarCategoria();
        }
    }

    public void cargarDatos(){
        tblCategoria.setItems(listarCategoria());
        colId.setCellValueFactory(new PropertyValueFactory<Categoria, Long>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nombreCategoria"));
    }

    public void cargarFormEditar(){
        Categoria categoria = (Categoria)tblCategoria.getSelectionModel().getSelectedItem();
        if(categoria != null){
            tfId.setText(Long.toString(categoria.getId()));
            tfNombre.setText(categoria.getNombreCategoria());
        }
    }

    public void LimpiarFormEditar(){
        tfId.clear();
        tfNombre.clear();
    }

    public ObservableList<Categoria> listarCategoria(){
        return FXCollections.observableList(categoriaService.listarCategoria());
    }

    public void agregarCategoria(){
        Categoria categoria = new Categoria();
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void editarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoria.setNombreCategoria(tfNombre.getText());
        categoriaService.guardarCategoria(categoria);
        cargarDatos();
    }

    public void eliminarCategoria(){
        Categoria categoria = categoriaService.buscarCategoriaPorId(Long.parseLong(tfId.getText()));
        categoriaService.eliminarCategoria(categoria);
        cargarDatos();
    }

    public void buscarCategoria() {
        try {
            if (tfBuscar.getText().isEmpty()) {
                cargarDatos();
                return;
            }
            Long id = Long.parseLong(tfBuscar.getText());
            Categoria categoria = categoriaService.buscarCategoriaPorId(id);
            if (categoria != null) {
                tfId.setText(Long.toString(categoria.getId()));
                tfNombre.setText(categoria.getNombreCategoria());
                ObservableList<Categoria> categoriasEncontradas = FXCollections.observableArrayList(categoria);
                tblCategoria.setItems(categoriasEncontradas);
            } else {
                LimpiarFormEditar();
                tblCategoria.setItems(FXCollections.observableArrayList());
            }
        } catch (NumberFormatException e) {
            LimpiarFormEditar();
            tblCategoria.setItems(FXCollections.observableArrayList());
        } catch (Exception e) {
            LimpiarFormEditar();
            tblCategoria.setItems(FXCollections.observableArrayList());
        }
    }
    
    
    
}
