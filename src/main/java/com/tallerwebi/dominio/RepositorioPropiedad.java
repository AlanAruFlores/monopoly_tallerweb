package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioPropiedad {

    List<Propiedad> obtenerTodasPropiedades();
    Propiedad obtenerPropiedadPorNroCasillero(Integer nroCasiillero);

}

