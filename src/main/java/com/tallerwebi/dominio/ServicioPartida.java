package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ExcesoDeJugadoresException;

import java.util.List;

public interface ServicioPartida {
    public void crearUnaPartidaNueva(Partida partida);
    public List<Partida> obtenerTodasLasPartidas();
    public Partida obtenerPartidaPorPartidaId(Long partidaId);
    public void unirseAPartida(Long partidaId,Usuario usuario) throws ExcesoDeJugadoresException;
    public List<Usuario> verUsuariosEnlaPartidaEspera(Long partidaId);

    public Usuario obtenerCreadoUsuarioDeUnaPartida(Long partidaId);

    public void salirDeLaPartida(Long partidaId, Long usuarioId);
}