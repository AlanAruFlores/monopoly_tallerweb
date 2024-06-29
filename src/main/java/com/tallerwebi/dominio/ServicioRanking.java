package com.tallerwebi.dominio;

import java.util.List;

public interface ServicioRanking {

    public List<Usuario> findAllSortedByVictorias();
}
