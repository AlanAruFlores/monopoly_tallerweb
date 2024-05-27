package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.RepositorioPartida;
import com.tallerwebi.dominio.ServicioPartida;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("servicioPartida")
public class ServicioPartidaImpl implements ServicioPartida {
    private RepositorioPartida  repositorioPartida;

    @Autowired
    public ServicioPartidaImpl(RepositorioPartida repositorioPartida) {
        this.repositorioPartida = repositorioPartida;
    }

    @Override
    public void crearUnaPartidaNueva(Partida partida) {
        
    }
}
