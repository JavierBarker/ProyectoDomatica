package org.javierbarker.bean;

public class Rol {
    private int codigoRol;
    private String rol;

    public Rol() {
    }

    public Rol(int codigoRol, String rol) {
        this.codigoRol = codigoRol;
        this.rol = rol;
    }

    public int getCodigoRol() {
        return codigoRol;
    }

    public void setCodigoRol(int codigoRol) {
        this.codigoRol = codigoRol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    @Override
    public String toString() {
        return rol ;
    }
    
    
}
