package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartidaUsuario {
    public void crearPartidaUsuario(PartidaUsuario partidaUsuario);
    public void actualizarPartidaUsuario();
    public void eliminarPartidaUsuario();
    public void eliminarPartidaUsuarioPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId);
    public List<Usuario> obtenerUsuariosEnUnaPartida(Long partidaId);
    public List<PartidaUsuario> obtenerPartidasUsuariosEnlaPartidaId(Long partidaId);
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId);
}

