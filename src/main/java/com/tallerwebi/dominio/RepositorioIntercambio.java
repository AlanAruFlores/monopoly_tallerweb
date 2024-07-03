package com.tallerwebi.dominio;

public interface RepositorioIntercambio {
    public void crearIntercambio(Intercambio intercambio);
    public Intercambio buscarIntercambioByDestinatarioId(PartidaUsuario partidaUsuario);
}
