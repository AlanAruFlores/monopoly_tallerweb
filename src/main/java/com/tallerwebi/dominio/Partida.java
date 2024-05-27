package com.tallerwebi.dominio;

import com.google.protobuf.Enum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Partida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private Usuario creadorJugador;

    private LocalDate fechaApertura;

    @OneToOne
    private Usuario turnoJugador;

    @Enumerated(EnumType.STRING)
    private EstadoPartida estadoPartida;

}
