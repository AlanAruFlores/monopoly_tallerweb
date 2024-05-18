package com.tallerwebi.dominio;

import javax.persistence.*;

import java.util.List;
import com.tallerwebi.dominio.Usuario;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @OneToOne
    private Usuario usuario;

    private Double saldo;

    /*
    @OneToMany
    private List<Propiedad> propiedades;
*/
    public Jugador(Usuario usuario, Integer id, Double saldo) {
        this.usuario = usuario;
        this.id = id;
        this.saldo = saldo;
    }

    public Jugador() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getSaldo() {
        return saldo;
    }

    public void setSaldo(Double saldo) {
        this.saldo = saldo;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}
