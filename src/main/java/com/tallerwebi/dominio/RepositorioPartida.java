package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartida {
    public void crearPartida(Partida partida);
    public List<Partida> obtenerPartidas();
    public Partida obtenerPartidaPorId(Long partidaId);

}
