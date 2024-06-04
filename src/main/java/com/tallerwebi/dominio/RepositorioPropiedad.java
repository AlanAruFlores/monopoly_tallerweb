package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPropiedad {
    public void actualizar(Propiedad propiedad);
    List<Propiedad> obtenerTodasPropiedades();
    Propiedad obtenerPropiedadPorNroCasillero(Integer nroCasiillero);
}

