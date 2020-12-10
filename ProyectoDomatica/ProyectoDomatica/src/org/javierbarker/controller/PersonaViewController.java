package org.javierbarker.controller;

import com.jfoenix.controls.*;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.converter.LocalDateStringConverter;
import org.javierbarker.bd.Conexion;
import org.javierbarker.bean.Persona;
import org.javierbarker.bean.Usuario;


public class PersonaViewController implements Initializable {
    Usuario user = Usuario.getInstance();
    private enum operaciones{NUEVO, GUARDAR, ELIMINAR, EDITAR, ACTUALIZAR, CANCELAR, NINGUNO};
    private operaciones tipoOperacion = operaciones.NINGUNO;
    private ObservableList<Persona> listaPersona;
    JFXDialogLayout dialogoLayout;
    JFXButton button1;
    JFXButton button2;
    JFXDialog dialogo;
    @FXML
    private StackPane spStackPane;
    
    @FXML
    private JFXTextField txtCodigoPersona;

    @FXML
    private JFXTextField txtDPI;

    @FXML
    private JFXTextField txtNombrePersona;

    @FXML
    private JFXDatePicker dtpFechaIngreso;

    @FXML
    private TableView tblPersona;

    @FXML
    private TableColumn colCodigoPersona;

    @FXML
    private TableColumn colDPI;

    @FXML
    private TableColumn colNombrePersona;

    @FXML
    private TableColumn colFechaIngreso;
    
    @FXML
    private JFXButton btnNuevo;
    
    @FXML
    private JFXButton btnEliminar;

    @FXML
    private JFXButton btnEditar;

    @FXML
    private JFXButton btnReporte;
    
    public void desactivarControles(){
        txtCodigoPersona.setEditable(false);
        txtDPI.setEditable(false);
        txtNombrePersona.setEditable(false);
        dtpFechaIngreso.setDisable(true);
    }
    
    public void activarControles(){
        txtCodigoPersona.setEditable(false);
        txtDPI.setEditable(true);
        txtNombrePersona.setEditable(true);
        dtpFechaIngreso.setDisable(false);
    }
    
    public void limpiarControles(){
        txtCodigoPersona.setText(null);
        txtDPI.setText(null);
        txtNombrePersona.setText(null);
        dtpFechaIngreso.setValue(null);
    }
    
    public void nuevo(){
        switch(tipoOperacion){
            case NINGUNO:
                limpiarControles();
                activarControles();
                btnNuevo.setText("Guardar");
                btnEliminar.setText("Cancelar");
                btnEditar.setDisable(true);
                btnReporte.setDisable(true);
                tipoOperacion = operaciones.GUARDAR;
            break;
            case GUARDAR:
                if(txtDPI.getText() == null || txtNombrePersona.getText() == null || dtpFechaIngreso.getValue() == null){
                    dialogoLayout = new JFXDialogLayout();
                    dialogoLayout.setHeading(new Text("Mensaje"));
                    dialogoLayout.setBody(new Text("Tiene Campos Vacíos"));
                    button1 = new JFXButton("Cerrar");
                    button1.setButtonType(JFXButton.ButtonType.FLAT);
                    button1.setStyle("-fx-background-color: #009df1;");
                    dialogoLayout.setActions(button1);
                    dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
                    button1.setOnAction(event1 ->{
                        dialogo.close();
                    });
                    dialogo.show();
                }else{
                    guardar();
                    desactivarControles();
                    limpiarControles();
                    btnNuevo.setText("Nuevo");
                    btnEliminar.setText("Eliminar");
                    btnEditar.setDisable(false);
                    btnReporte.setDisable(false);
                    tipoOperacion = operaciones.NINGUNO;
                    cargarDatos(); 
                }
            break;
        }
    }
    
    public void editar(){
        switch(tipoOperacion){
            case NINGUNO:
                if(tblPersona.getSelectionModel().getSelectedItem() != null){
                    btnEditar.setText("Actualizar");
                    btnReporte.setText("Cancelar");
                    btnNuevo.setDisable(true);
                    btnEliminar.setDisable(true);
                    activarControles();
                    tipoOperacion = operaciones.ACTUALIZAR;
                }else{
                    dialogoLayout = new JFXDialogLayout();
                    dialogoLayout.setHeading(new Text("Mensaje"));
                    dialogoLayout.setBody(new Text("Debe seleccionar un Registro para Editar"));
                    button1 = new JFXButton("Cerrar");
                    button1.setButtonType(JFXButton.ButtonType.FLAT);
                    button1.setStyle("-fx-background-color: #009df1;");
                    dialogoLayout.setActions(button1);
                    dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
                    button1.setOnAction(event1 ->{
                        dialogo.close();
                    });
                    
                    dialogo.show();
                }
            break;
            case ACTUALIZAR:
                dialogoLayout = new JFXDialogLayout();
                dialogoLayout.setHeading(new Text("Editar Persona"));
                dialogoLayout.setBody(new Text("¿Esta seguro de Actualizar el Registro?"));
                button1 = new JFXButton(" Si ");
                button1.setButtonType(JFXButton.ButtonType.FLAT);
                button1.setStyle("-fx-background-color: #2EFE2E;");
                button2 = new JFXButton(" No ");
                button2.setButtonType(JFXButton.ButtonType.FLAT);
                button2.setStyle("-fx-background-color: #FE2E2E;");
                dialogoLayout.setActions(button1, button2);
                dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
                button1.setOnAction(event1 ->{
                    dialogo.close();
                    actualizar();
                    btnEditar.setText("Editar");
                    btnReporte.setText("Reporte");
                    btnNuevo.setDisable(false);
                    btnEliminar.setDisable(false);
                    tipoOperacion = operaciones.NINGUNO;
                    cargarDatos();
                });
                button2.setOnAction(event2 ->{
                    dialogo.close();
                });
                dialogo.show();
            break;
        }
    }
    
    public void eliminar(){
        switch(tipoOperacion){
            case GUARDAR:
                desactivarControles();
                limpiarControles();
                btnNuevo.setText("Nuevo");
                btnEliminar.setText("Eliminar");
                btnEditar.setDisable(false);
                btnReporte.setDisable(false);
                tipoOperacion = operaciones.NINGUNO;
            break;
            default:
                if(tblPersona.getSelectionModel().getSelectedItem() != null){
                    dialogoLayout = new JFXDialogLayout();
                    dialogoLayout.setHeading(new Text("Eliminar Persona"));
                    dialogoLayout.setBody(new Text("¿Esta seguro de Eliminar a la Persona?"));
                    button1 = new JFXButton(" Si ");
                    button1.setButtonType(JFXButton.ButtonType.FLAT);
                    button1.setStyle("-fx-background-color: #2EFE2E;");
                    button2 = new JFXButton(" No ");
                    button2.setButtonType(JFXButton.ButtonType.FLAT);
                    button2.setStyle("-fx-background-color: #FE2E2E;");
                    dialogoLayout.setActions(button1, button2);
                    dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
                    button1.setOnAction(event1 ->{
                        dialogo.close();
                        eliminar1();
                    });
                    button2.setOnAction(event2 ->{
                        dialogo.close();
                    });
                    dialogo.show();
                    
                }else{
                    dialogoLayout = new JFXDialogLayout();
                    dialogoLayout.setHeading(new Text("Mensaje"));
                    dialogoLayout.setBody(new Text("Debe de seleccionar un registro en la Tabla"));
                    button1 = new JFXButton("Cerrar");
                    button1.setButtonType(JFXButton.ButtonType.FLAT);
                    button1.setStyle("-fx-background-color: #009df1;");
                    dialogoLayout.setActions(button1);
                    dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
                    button1.setOnAction(event1 ->{
                        dialogo.close();
                    });
                    dialogo.show();
                }
            break;
            
        }
    }
    
    public void reporte(){
        switch(tipoOperacion){
            case ACTUALIZAR:
                desactivarControles();
                limpiarControles();
                btnEditar.setText("Editar");
                btnReporte.setText("Reporte");
                btnNuevo.setDisable(false);
                btnEliminar.setDisable(false);
                tipoOperacion = operaciones.NINGUNO;
            break;
        }
    }
    
    public void guardar(){
        try{
            Persona registro = new Persona();
            registro.setDPI(txtDPI.getText());
            registro.setNombrePersona(txtNombrePersona.getText());
            registro.setFechaIngreso(dtpFechaIngreso.getValue());
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_AgregarPersona(?,?,?)}");
            procedimiento.setString(1, registro.getDPI());
            procedimiento.setString(2, registro.getNombrePersona());
            procedimiento.setDate(3, Date.valueOf(registro.getFechaIngreso()));
            procedimiento.execute();
            listaPersona.add(registro);
            
            dialogoLayout = new JFXDialogLayout();
            dialogoLayout.setHeading(new Text("Mensaje"));
            dialogoLayout.setBody(new Text("Registro Guardado"));
            button1 = new JFXButton("Cerrar");
            button1.setButtonType(JFXButton.ButtonType.FLAT);
            button1.setStyle("-fx-background-color: #009df1;");
            dialogoLayout.setActions(button1);
            dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
            button1.setOnAction(event1 ->{
                dialogo.close();
            });
            dialogo.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void actualizar(){
        try{
            Persona registro = (Persona)tblPersona.getSelectionModel().getSelectedItem();
            registro.setDPI(txtDPI.getText());
            registro.setNombrePersona(txtNombrePersona.getText());
            registro.setFechaIngreso(dtpFechaIngreso.getValue());
            
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ActualizarPersona(?,?,?,?)}");
            procedimiento.setInt(1, registro.getCodigoPersona());
            procedimiento.setString(2, registro.getDPI());
            procedimiento.setString(3, registro.getNombrePersona());
            procedimiento.setDate(4, Date.valueOf(registro.getFechaIngreso()));
            procedimiento.execute();
            
            dialogoLayout = new JFXDialogLayout();
            dialogoLayout.setHeading(new Text("Mensaje"));
            dialogoLayout.setBody(new Text("Datos Actualizados"));
            button1 = new JFXButton("Cerrar");
            button1.setButtonType(JFXButton.ButtonType.FLAT);
            button1.setStyle("-fx-background-color: #009df1;");
            dialogoLayout.setActions(button1);
            dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
            button1.setOnAction(event1 ->{
                dialogo.close();
            });
            dialogo.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void eliminar1(){
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_EliminarPersona(?)}");
            procedimiento.setInt(1, ((Persona)tblPersona.getSelectionModel().getSelectedItem()).getCodigoPersona());
            procedimiento.execute();
            listaPersona.remove(tblPersona.getSelectionModel().getSelectedIndex());
            limpiarControles();
            
            dialogoLayout = new JFXDialogLayout();
            dialogoLayout.setHeading(new Text("Mensaje"));
            dialogoLayout.setBody(new Text("Persona Eliminada con Exito!"));
            button1 = new JFXButton("Cerrar");
            button1.setButtonType(JFXButton.ButtonType.FLAT);
            button1.setStyle("-fx-background-color: #009df1;");
            dialogoLayout.setActions(button1);
            dialogo = new JFXDialog(spStackPane, dialogoLayout, JFXDialog.DialogTransition.CENTER);
            button1.setOnAction(event1 ->{
                dialogo.close();
            });
            dialogo.show();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void cargarDatos(){
        tblPersona.setItems(getPersona());
        colCodigoPersona.setCellValueFactory(new PropertyValueFactory<Persona, Integer>("codigoPersona"));
        colDPI.setCellValueFactory(new PropertyValueFactory<Persona, String>("DPI"));
        colNombrePersona.setCellValueFactory(new PropertyValueFactory<Persona, String>("nombrePersona"));
        colFechaIngreso.setCellValueFactory(new PropertyValueFactory<Persona, Date>("fechaIngreso"));
        desactivarControles();
    }
    
    public ObservableList<Persona> getPersona(){
        ArrayList<Persona> lista = new ArrayList<Persona>();
        try{
            PreparedStatement procedimiento = Conexion.getInstance().getConexion().prepareCall("{call sp_ListarPersonas()}");
            ResultSet resultado = procedimiento.executeQuery();
            while(resultado.next()){
                LocalDate fecha = resultado.getDate(4).toLocalDate();
                lista.add(new Persona(
                    resultado.getInt("codigoPersona"),
                    resultado.getString("DPI"),
                    resultado.getString("nombrePersona"),
                    fecha
                    ));
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return listaPersona = FXCollections.observableArrayList(lista);
    }
    
    public void seleccionarElemento(){
        txtCodigoPersona.setText(String.valueOf(((Persona)tblPersona.getSelectionModel().getSelectedItem()).getCodigoPersona()));
        txtDPI.setText(String.valueOf(((Persona)tblPersona.getSelectionModel().getSelectedItem()).getDPI()));
        txtNombrePersona.setText(String.valueOf(((Persona)tblPersona.getSelectionModel().getSelectedItem()).getNombrePersona()));
        dtpFechaIngreso.setValue(((Persona)tblPersona.getSelectionModel().getSelectedItem()).getFechaIngreso());
    }
    
    public void botonNuevo(){
        btnNuevo.setStyle("-fx-background-color: #193983;");
    }
    
    public void botonEliminar(){
        btnEliminar.setStyle("-fx-background-color: #193983;");
    }
    
    public void botonEditar(){
        btnEditar.setStyle("-fx-background-color: #193983;");
    }
    
    public void botonReporte(){
        btnReporte.setStyle("-fx-background-color: #193983;");
    }
    
    public void botonesNormales(){
        btnNuevo.setStyle("-fx-background-color: #009df1;");
        btnEliminar.setStyle("-fx-background-color: #009df1;");
        btnEditar.setStyle("-fx-background-color: #009df1;");
        btnReporte.setStyle("-fx-background-color: #009df1;");
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("YYYY/MM/dd");
        dtpFechaIngreso.setConverter(new LocalDateStringConverter(format,null));
        cargarDatos();
        

        if(user.getRol_codigoRol() == 2){
            btnNuevo.setDisable(true);
            btnEliminar.setDisable(true);
            btnEditar.setDisable(true);
            btnReporte.setDisable(true);
        }
    }    
    
}
