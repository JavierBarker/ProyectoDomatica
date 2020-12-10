package org.javierbarker.bean;

public class Usuario {
    private static Usuario user;
    private String usuario;
    private String password0;
    private int rol_codigoRol;

    private Usuario() {
    }

    public Usuario(String usuario, String password0, int rol_codigoRol) {
        this.usuario = usuario;
        this.password0 = password0;
        this.rol_codigoRol = rol_codigoRol;
    }
    
    public static Usuario getInstance(){
        if(user == null){
            user = new Usuario();
        }
        return user;
    }
    
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword0() {
        return password0;
    }

    public void setPassword0(String password0) {
        this.password0 = password0;
    }

    public int getRol_codigoRol() {
        return rol_codigoRol;
    }

    public void setRol_codigoRol(int rol_codigoRol) {
        this.rol_codigoRol = rol_codigoRol;
    }
    
    
}
