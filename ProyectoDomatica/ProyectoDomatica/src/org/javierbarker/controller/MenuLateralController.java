package org.javierbarker.controller;

import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.javierbarker.bean.Usuario;
import org.javierbarker.system.MainApp;

public class MenuLateralController implements Initializable {
    Usuario user = Usuario.getInstance();
    private MenuViewController pagina;
    @FXML
    private JFXButton btnInicio;
    @FXML
    private JFXButton btnPersona;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
    }
    
}
