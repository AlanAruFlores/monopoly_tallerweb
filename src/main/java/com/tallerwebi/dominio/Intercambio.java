package com.tallerwebi.dominio;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

import com.tallerwebi.dominio.PartidaUsuario;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Intercambio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PartidaUsuario emisor;

    @ManyToOne
    private PartidaUsuario receptor;

    @Enumerated(EnumType.STRING)
    private EstadoIntercambio estado;

    private Double saldoEmisor;

    private Double saldoReceptor;

    @OneToMany(mappedBy = "intercambio", fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<IntercambioPropiedades> intercambioPropiedades;

    public Intercambio(PartidaUsuario emisor, PartidaUsuario receptor, EstadoIntercambio estado, Double saldoEmisor, Double saldoReceptor, List<IntercambioPropiedades> intercambioPropiedades) {
        this.emisor = emisor;
        this.receptor = receptor;
        this.estado = estado;
        this.saldoEmisor = saldoEmisor;
        this.saldoReceptor = saldoReceptor;
        this.intercambioPropiedades = intercambioPropiedades;
    }
}
