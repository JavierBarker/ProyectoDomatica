package org.javierbarker.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.concurrent.Task;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import org.javierbarker.bean.Usuario;
import org.javierbarker.system.MainApp;

public class MenuViewController implements Initializable, EventHandler{
    private final String PAQUETE_VISTA = "/org/javierbarker/view/";
    Usuario user = Usuario.getInstance();
    private MainApp escenarioPrincipal;
    @FXML
    private StackPane spInicio;

    @FXML
    private JFXDrawer drwMenuLateral;

    @FXML
    private JFXHamburger hmgrBurger;

    @FXML
    private JFXButton btnCerrarSesion;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource(PAQUETE_VISTA+"InicioView.fxml"));
            spInicio.getChildren().setAll(root);
            VBox vbox = FXMLLoader.load(getClass().getResource("/org/javierbarker/view/MenuLateral.fxml"));
            drwMenuLateral.setSidePane(vbox);
            
            HamburgerBackArrowBasicTransition burger = new HamburgerBackArrowBasicTransition(hmgrBurger);
            burger.setRate(-1);
            hmgrBurger.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) ->{
                burger.setRate(burger.getRate() * -1);
                burger.play();
                if (drwMenuLateral.isOpened()){
                    drwMenuLateral.close();
                    menuLateralClose();
                }else{
                    drwMenuLateral.open();
                    drwMenuLateral.setVisible(true);
                }
            });
            
            
            for(Node node : vbox.getChildren()){
                if(node.getAccessibleText() != null){
                    node.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {//se revisan las importaciones que sean javafx
                        switch(node.getAccessibleText()){
                            case "Material_One":
                                try {
                                    Parent inicio = FXMLLoader.load(getClass().getResource(PAQUETE_VISTA+"InicioView.fxml"));
                                    spInicio.getChildren().setAll(inicio);
                                } catch (IOException ex) {
                                    Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;

                            
                            case "Material_Two":
                                try {
                                    Parent persona = FXMLLoader.load(getClass().getResource(PAQUETE_VISTA+"PersonaView.fxml"));
                                    spInicio.getChildren().setAll(persona);
                                } catch (IOException ex) {
                                    Logger.getLogger(MenuViewController.class.getName()).log(Level.SEVERE, null, ex);
                                }
                                break;
                        }
                    });
                }
            }
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
    public void menuLateralClose(){
        Task task = tarea(20);
        task.setOnSucceeded(e ->{
            drwMenuLateral.setVisible(false);
        });
        Thread th = new Thread(task);
        th.start();
    }
    
    public Task tarea(int seconds){
        return new Task(){
           @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconds; i++) {
                   updateProgress(i+1, seconds);
                   Thread.sleep(seconds);
                   
               }
                return null;
            } 
        };
    }
    
    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    public void cerrarSesion(){
        escenarioPrincipal.login();
        user.setUsuario(null);
        user.setPassword0(null);
        user.setRol_codigoRol(0);
    }

    @Override
    public void handle(Event arg0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
