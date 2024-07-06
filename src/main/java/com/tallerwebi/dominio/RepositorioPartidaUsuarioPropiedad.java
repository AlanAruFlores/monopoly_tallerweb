package com.tallerwebi.dominio;

public interface RepositorioPartidaUsuarioPropiedad {
    public void crearPartidaUsuarioPropiedad(PartidaUsuarioPropiedad pup);
    public void eliminarPartidaUsuarioPropiedadPorJugadorYPropiedad(PartidaUsuario pu , Propiedad p);
}
