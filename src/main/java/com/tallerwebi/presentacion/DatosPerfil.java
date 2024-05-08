package com.tallerwebi.presentacion;

public class DatosPerfil {
    private String nombre;
    private String email;
    private String contraseniaActual;
    private String contraseniaNueva;
    private String repiteContraseniaNueva;

    public DatosPerfil() {}

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContraseniaActual() {
        return contraseniaActual;
    }

    public void setContraseniaActual(String contraseniaActual) {
        this.contraseniaActual = contraseniaActual;
    }

    public String getContraseniaNueva() {
        return contraseniaNueva;
    }

    public void setContraseniaNueva(String contraseniaNueva) {
        this.contraseniaNueva = contraseniaNueva;
    }

    public String getRepiteContraseniaNueva() {
        return repiteContraseniaNueva;
    }

    public void setRepiteContraseniaNueva(String repiteContraseniaNueva) {
        this.repiteContraseniaNueva = repiteContraseniaNueva;
    }

}
