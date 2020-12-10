package org.javierbarker.bean;

import java.time.LocalDate;

public class Persona {
    private int codigoPersona;
    private String DPI;
    private String nombrePersona;
    private LocalDate fechaIngreso;

    public Persona() {
    }

    public Persona(int codigoPersona, String DPI, String nombrePersona, LocalDate fechaIngreso) {
        this.codigoPersona = codigoPersona;
        this.DPI = DPI;
        this.nombrePersona = nombrePersona;
        this.fechaIngreso = fechaIngreso;
    }

    public int getCodigoPersona() {
        return codigoPersona;
    }

    public void setCodigoPersona(int codigoPersona) {
        this.codigoPersona = codigoPersona;
    }

    public String getDPI() {
        return DPI;
    }

    public void setDPI(String DPI) {
        this.DPI = DPI;
    }

    public String getNombrePersona() {
        return nombrePersona;
    }

    public void setNombrePersona(String nombrePersona) {
        this.nombrePersona = nombrePersona;
    }

    public LocalDate getFechaIngreso() {
        return fechaIngreso;
    }

    public void setFechaIngreso(LocalDate fechaIngreso) {
        this.fechaIngreso = fechaIngreso;
    }

    
    
    
    
}
