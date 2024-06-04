package com.tallerwebi.dominio;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PartidaUsuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Partida partida;

    @ManyToOne
    private Usuario usuario;

    private Integer posicionCasilla;
    private Double saldo;

    @Enumerated(EnumType.STRING)
    private Color colorUsuario;

    @OneToMany(mappedBy = "partidaUsuario", fetch = FetchType.EAGER)
    private List<PartidaUsuarioPropiedad> propiedades;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PartidaUsuario that = (PartidaUsuario) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}