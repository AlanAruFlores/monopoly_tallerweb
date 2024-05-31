package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPartidaUsuario {
    public void crearPartidaUsuario(PartidaUsuario partidaUsuario);
    public void actualizarPartidaUsuario();
    public void eliminarPartidaUsuario();
    public List<Usuario> obtenerUsuariosEnUnaPartida(Long partidaId);
}
