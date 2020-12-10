package org.javierbarker.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.javierbarker.controller.LogInViewController;
import org.javierbarker.controller.MenuViewController;


public class MainApp extends Application {
    private final String PAQUETE_VISTA = "/org/javierbarker/view/";
    private Stage escenarioPrincipal;
    private Scene escena;
    @Override
    public void start(Stage escenarioPrincipal) throws Exception{
        this.escenarioPrincipal = escenarioPrincipal;
        this.escenarioPrincipal.setTitle("Login");
        escenarioPrincipal.getIcons().add(new Image("org/javierbarker/img/usuario.png"));
        login();
        escenarioPrincipal.show();
    }

    public void login(){
        try{
            LogInViewController login = (LogInViewController) cambiarEscena("LogInView.fxml",493,497);
            login.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void menu(){
        try{
            MenuViewController menu = (MenuViewController) cambiarEscena("MenuView.fxml",762,630);
            menu.setEscenarioPrincipal(this);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception{
        Initializable resultado = null;
        FXMLLoader cargadorFxml = new FXMLLoader();
        InputStream archivo = MainApp.class.getResourceAsStream(PAQUETE_VISTA+fxml);
        cargadorFxml.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFxml.setLocation(MainApp.class.getResource(PAQUETE_VISTA+fxml));
        escena = new Scene((AnchorPane)cargadorFxml.load(archivo),ancho,alto);
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable)cargadorFxml.getController();
        return resultado;
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
