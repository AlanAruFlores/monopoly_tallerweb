package com.tallerwebi.dominio;

public interface RepositorioPartidaUsuarioPropiedad {
    public void crearPartidaUsuarioPropiedad(PartidaUsuarioPropiedad pup);
    public void eliminarPartidaUsuarioPropiedadPorJugadorYPropiedad(PartidaUsuario pu , Propiedad p);
    public PartidaUsuarioPropiedad obtenerPartidaUsuarioPropiedadPorId(Integer id);
    public void actualizarPartidaUsuarioPropiedad(PartidaUsuarioPropiedad pup);
}
