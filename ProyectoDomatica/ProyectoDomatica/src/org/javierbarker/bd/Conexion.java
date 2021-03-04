package org.javierbarker.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    public Conexion(){
        String usuario = "unbagslop0xidpne";
        String password = "Vd7wCLUPbLovViUjoccs";
        String host = "byzuglvnczebyf5s4a4d-mysql.services.clever-cloud.com";
        String db = "byzuglvnczebyf5s4a4d";
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+db+"?serverTimezone=UTC",usuario,password);
        }catch(ClassNotFoundException | SQLException | InstantiationException | IllegalAccessException e){
            e.printStackTrace();
        }
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
    
    public static Conexion getInstance(){
        if (instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
}
