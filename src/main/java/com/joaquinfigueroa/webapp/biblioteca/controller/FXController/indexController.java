package com.joaquinfigueroa.webapp.biblioteca.controller.FXController;
 
import java.net.URL;
import java.util.ResourceBundle;
 
import org.springframework.stereotype.Component;

import com.joaquinfigueroa.webapp.biblioteca.system.Main;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import lombok.Setter;
 
@Component
public class indexController implements Initializable {

    @Setter
    private Main stage;
    
    @Override
    public void initialize(URL url, ResourceBundle resources) {
 
    }

    @FXML
    MenuItem btnCategoria, btnCliente, btnEmpleado, btnLibro, btnPrestamo;

    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCategoria){
            stage.menuCategoria();
        }else if(event.getSource() == btnCliente){
            stage.menuClientes();
        }else if(event.getSource() == btnEmpleado){
            stage.menuEmpleados();
        }else if(event.getSource() == btnLibro ){
            stage.menuLibros();
        }else if(event.getSource() == btnPrestamo){
            stage.menuPrestamos();
        }
    }

 
}