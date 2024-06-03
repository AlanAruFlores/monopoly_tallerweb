package com.tallerwebi.dominio;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.jfr.Unsigned;
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
public class PartidaUsuarioPropiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    private Propiedad propiedad;

    //Jugador en la partida
    @ManyToOne
    @JsonBackReference
    private PartidaUsuario partidaUsuario;
}
