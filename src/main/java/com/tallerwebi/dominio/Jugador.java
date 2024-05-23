package com.tallerwebi.dominio;

import javax.persistence.*;

@Entity
public class Jugador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario usuario;

    private Integer posicionCasilla;

    private Double saldo;

    public Jugador(Usuario usuario, Long id, Integer posicionCasilla,Double saldo) {
        this.usuario = usuario;
        this.id = id;
        this.posicionCasilla = posicionCasilla;
        this.saldo = saldo;
    }

    public Jugador() {
    }

    public Integer getPosicionCasilla() {
        return posicionCasilla;
    }

    public void setPosicionCasilla(Integer posicionCasilla) {
        this.posicionCasilla = posicionCasilla;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
