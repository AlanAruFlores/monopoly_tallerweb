package com.tallerwebi.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Partida partida;

    @OneToOne
    private Usuario usuario;

    private Integer posicionCasilla;
    private Double saldo;

    @OneToMany(mappedBy = "propiedad")
    private List<PartidaUsuarioPropiedad> propiedades;

}
