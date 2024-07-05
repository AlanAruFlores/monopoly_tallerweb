package com.tallerwebi.dominio;

public interface RepositorioIntercambio {
    public void crearIntercambio(Intercambio intercambio);
    public Intercambio getIntercambioPorId(Long id);
    public void actualizarIntercambio(Intercambio intercambio);
    public Intercambio buscarIntercambioByEmisorId(PartidaUsuario partidaUsuario);
    public Intercambio buscarIntercambioByDestinatarioId(PartidaUsuario partidaUsuario);
    public Intercambio buscarIntercambioByEmisorIdAndDestinatarioId(Long idEmisor, Long idReceptor);
    public void eliminarIntercambioPorId(Long id);
}
