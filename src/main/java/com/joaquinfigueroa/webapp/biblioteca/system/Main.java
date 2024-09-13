package com.joaquinfigueroa.webapp.biblioteca.system;

import org.springframework.context.ConfigurableApplicationContext;

import com.joaquinfigueroa.webapp.biblioteca.BibliotecaApplication;
import com.joaquinfigueroa.webapp.biblioteca.controller.FXController.categoriasFXController;
import com.joaquinfigueroa.webapp.biblioteca.controller.FXController.clientesFXController;
import com.joaquinfigueroa.webapp.biblioteca.controller.FXController.empleadosFXController;
import com.joaquinfigueroa.webapp.biblioteca.controller.FXController.indexController;
import com.joaquinfigueroa.webapp.biblioteca.controller.FXController.librosFXController;
import com.joaquinfigueroa.webapp.biblioteca.controller.FXController.prestamoFXController;

import java.io.IOException;
import java.io.InputStream;

import org.springframework.boot.builder.SpringApplicationBuilder;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application{
    private ConfigurableApplicationContext applicationContext;
    private Stage stage;
    private Scene scene; 

    @Override
    public void init(){
        this.applicationContext = new SpringApplicationBuilder(BibliotecaApplication.class).run();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.stage = primaryStage;
        stage.setTitle("Biblioteca Spring");
        indexView();
        stage.show();
    }
    
    public Initializable switchScene(String fxmlName, int width, int height) throws IOException{
        Initializable resultado = null;
        FXMLLoader loader = new FXMLLoader();

        loader.setControllerFactory(applicationContext::getBean);
        InputStream archivo = Main.class.getResourceAsStream("/templates/" + fxmlName);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource("/templates/" + fxmlName));

        scene = new Scene((AnchorPane) loader.load(archivo), width, height);
        stage.setScene(scene);
        stage.sizeToScene();

        resultado = (Initializable)loader.getController();
        return resultado;
    }

    public void indexView(){
        try {
            indexController indexView = (indexController)switchScene("index.fxml", 1129, 686);
            indexView.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuCategoria(){
        try {
            categoriasFXController menuCategoria = (categoriasFXController)switchScene("categoria.fxml", 600, 400);
            menuCategoria.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuClientes(){
        try {
            clientesFXController menuCliente = (clientesFXController)switchScene("cliente.fxml", 1129, 686);
            menuCliente.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuEmpleados(){
        try {
            empleadosFXController menuEmpleado = (empleadosFXController)switchScene("empleado.fxml", 1129, 686);
            menuEmpleado.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuLibros(){
        try {
            librosFXController menuLibro = (librosFXController)switchScene("libro.fxml", 1129, 686);
            menuLibro.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuPrestamos(){
        try {
            prestamoFXController menuPrestamo = (prestamoFXController)switchScene("prestamo.fxml", 1129, 686);
            menuPrestamo.setStage(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}