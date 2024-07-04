package com.tallerwebi.dominio;

public interface RepositorioIntercambio {
    public void crearIntercambio(Intercambio intercambio);
    public Intercambio buscarIntercambioByEmisorId(PartidaUsuario partidaUsuario);
    public Intercambio buscarIntercambioByDestinatarioId(PartidaUsuario partidaUsuario);
    public Intercambio buscarIntercambioByEmisorIdAndDestinatarioId(Long idEmisor, Long idReceptor);


}
