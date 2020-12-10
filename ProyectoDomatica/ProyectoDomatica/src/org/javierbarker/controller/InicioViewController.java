package org.javierbarker.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import org.javierbarker.bean.Usuario;

public class InicioViewController implements Initializable {
    Usuario user = Usuario.getInstance();
    @FXML
    private Label lblMensajeInicio;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(user.getRol_codigoRol() == 1){
            lblMensajeInicio.setText("Bienvenido Usuario : "+user.getUsuario());
        }else{
            lblMensajeInicio.setText("Bienvenido Usuario : "+user.getUsuario());
        }
    }    
    
}
