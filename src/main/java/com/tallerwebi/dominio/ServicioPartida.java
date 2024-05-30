package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioPartida {
    public void crearUnaPartidaNueva(Partida partida);
    public List<Partida> obtenerTodasLasPartidas();
}
