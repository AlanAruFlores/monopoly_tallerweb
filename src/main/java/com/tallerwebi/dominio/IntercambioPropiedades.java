package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IntercambioPropiedades {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Intercambio intercambio;

    @ManyToOne
    private Propiedad propiedadEmisor;

    @ManyToOne
    private Propiedad propiedadReceptor;

    public IntercambioPropiedades(Intercambio intercambio, Propiedad propiedadEmisor, Propiedad propiedadReceptor) {
        this.intercambio = intercambio;
        this.propiedadEmisor = propiedadEmisor;
        this.propiedadReceptor = propiedadReceptor;
    }
}
