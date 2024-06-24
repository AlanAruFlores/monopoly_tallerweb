package com.tallerwebi.dominio;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
    private String password;
    private String repitePassword;
    private String nombreUsuario;
    private Integer victorias;
    private String imagenPerfil;

    public Usuario(String nombre, String email, String password, String apellido,
                   String nombreUsuario, String repitePassword) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.repitePassword = repitePassword;
        this.victorias = 0;
        this.imagenPerfil = "/imagenes/fotosPerfil/perfil1.png";
    }

    public Usuario(){}

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getApellido() {
        return apellido;
    }
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    public String getRepitePassword() {
        return repitePassword;
    }
    public void setRepitePassword(String repitePassword) {
        this.repitePassword = repitePassword;
    }
    public String getNombreUsuario() {
        return nombreUsuario;
    }
    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }
    public Integer getVictorias() {
        return victorias;
    }
    public void setVictorias(Integer victorias) {
        this.victorias = victorias;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getImagenPerfil() {
        return imagenPerfil;}

    public void setImagenPerfil(String imagenPerfil) {
        this.imagenPerfil = imagenPerfil;}

    public void setEsAdmin(boolean b) {
    }

    public void setBaneado(boolean b) {
    }

    public void setMotivoBaneo(String motivo) {
    }
}