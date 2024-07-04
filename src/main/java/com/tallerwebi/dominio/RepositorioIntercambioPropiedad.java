package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioIntercambioPropiedad {
    public void crearIntercambioPropiedad(IntercambioPropiedades intercambioPropiedades);
    public List<IntercambioPropiedades> obtenerIntercambioPropiedadesPorIntercambio(Intercambio intercambio);
}
