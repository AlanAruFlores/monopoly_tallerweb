package com.tallerwebi.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

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
    private String imagenPerfil;
    public Boolean baneado;

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
        this.baneado = false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Usuario usuario = (Usuario) o;
        return Objects.equals(id, usuario.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    public void setEsAdmin(boolean b) {}

}