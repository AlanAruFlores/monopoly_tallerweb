package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPropiedad {
    public void actualizar(Propiedad propiedad);
    List<Propiedad> obtenerTodasPropiedades();
    List<Propiedad> obtenerPropiedadesPorJugadorId(Long jugadorId);
    Propiedad obtenerPropiedadPorNroCasillero(Integer nroCasiillero);

}

