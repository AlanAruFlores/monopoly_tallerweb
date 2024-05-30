package com.tallerwebi.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    public Usuario(String nombre, String email, String password, String apellido, String nombreUsuario, String repitePassword) {
        this.nombre = nombre;
        this.email = email;
        this.password = password;
        this.apellido = apellido;
        this.nombreUsuario = nombreUsuario;
        this.repitePassword = repitePassword;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", repitePassword='" + repitePassword + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", victorias=" + victorias +
                '}';
    }
}