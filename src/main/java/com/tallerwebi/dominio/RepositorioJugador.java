package com.tallerwebi.dominio;

public interface RepositorioJugador {
    public void guardar(Jugador jugador);
    public Jugador obtenerJugador(Long usuarioId);
    public void actualizar(Jugador jugador);
}
