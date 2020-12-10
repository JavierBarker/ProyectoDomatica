package org.javierbarker.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import org.javierbarker.bd.Conexion;
import org.javierbarker.bean.Rol;
import org.javierbarker.bean.Usuario;
import org.javierbarker.system.MainApp;


public class LogInViewController implements Initializable {
    private ObservableList <Rol> listaRoles;
    private MainApp escenarioPrincipal;
    @FXML
    private JFXTextField txtUsuario;
    @FXML
    private JFXPasswordField txtPassword;
    @FXML
    private JFXComboBox cmbRol;
    @FXML
    private JFXButton btnLogin;
    @FXML
    private Label lblMensaje;
    @FXML
    private ProgressIndicator piProgreso;
    
    
    
    public void cargarDatos(){
        cmbRol.setItems(getRol());
    }
    
    public void validarCamposTXT(){
        if(txtUsuario.getText() != "" && txtPassword.getText() != "" && cmbRol.getValue() != null){
            validacion();
        }else{
            lblMensaje.setText("Campos Vacíos");
        }
    }
    
    public void validacion(){
        Usuario user = Usuario.getInstance();
        user.setUsuario(txtUsuario.getText());
        user.setPassword0(txtPassword.getText());
        user.setRol_codigoRol(((Rol)cmbRol.getSelectionModel().getSelectedItem()).getCodigoRol());
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_BuscarUsuario(?,?,?)}");
            procedimiento.setString(1, user.getUsuario());
            procedimiento.setString(2, user.getPassword0());
            procedimiento.setInt(3, user.getRol_codigoRol());
            ResultSet resultado = procedimiento.executeQuery();
            if(resultado.next()){
                //menuPrincipal();
                progressBar();
            }else{
                lblMensaje.setText("Usuario, Contraseña o Rol son Incorrectos");
                txtUsuario.setText("");
                txtPassword.setText("");
                cmbRol.getSelectionModel().clearSelection();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void progressBar(){
        piProgreso.setVisible(true);
        btnLogin.setVisible(false);
        Task task = tarea(35);
        piProgreso.progressProperty().unbind();//elimina todo lo que este elazado al objeto
        piProgreso.progressProperty().bind(task.progressProperty());//lo enlaza a una tarea
        task.setOnSucceeded(e ->{
            menuPrincipal();
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
    
    public ObservableList<Rol> getRol(){
        ArrayList<Rol> lista = new ArrayList<Rol>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarRoles()}");
            ResultSet resultado = procedimiento.executeQuery();
                while(resultado.next()){
                    lista.add(new Rol(
                            resultado.getInt("codigoRol"),
                            resultado.getString("rol")
                    ));
                }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaRoles = FXCollections.observableArrayList(lista);
    }
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    

    public MainApp getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(MainApp escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    

    
    public void menuPrincipal(){
        escenarioPrincipal.menu();
    }
    
    
}
