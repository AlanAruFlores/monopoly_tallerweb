package com.tallerwebi.presentacion;

public class DatosPerfil {
    private String nombre;
    private String nombreUsuario;
    private String email;
    private String contraseniaActual;
    private String contraseniaNueva;
    private String repiteContraseniaNueva;
    private String imagen;
    private Long userId;

    public DatosPerfil() {

    }

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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public long getId() {
        return userId;
    }

    public void setId(long id) {
        this.userId = id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
}
