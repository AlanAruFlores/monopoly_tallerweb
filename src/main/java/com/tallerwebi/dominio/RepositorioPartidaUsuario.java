package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartidaUsuario {
    public void crearPartidaUsuario(PartidaUsuario partidaUsuario);
    public void actualizarPartidaUsuario(PartidaUsuario partidaUsuario);
    public List<Usuario> obtenerUsuariosEnUnaPartida(Long partidaId);
    public List<PartidaUsuario> obtenerPartidasUsuariosEnlaPartidaId(Long partidaId);
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId);
    public PartidaUsuario obtenerUsuarioPartidaPorUsuarioId(Long usuarioId);
    public List<Color> obtenerColoresJugadoresUsuados(Long partidaId);
    public void eliminarPartidaUsuario(Long partidaId, Long usuarioId);
    public void cambiarEstadoPorId(Long partidaUsuarioId, EstadoActividad estado);
}

