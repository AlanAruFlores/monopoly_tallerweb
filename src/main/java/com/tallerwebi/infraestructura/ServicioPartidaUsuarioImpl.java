package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.RepositorioPartidaUsuario;
import com.tallerwebi.dominio.ServicioPartidaUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioPartidaUsuario")
@Transactional
public class ServicioPartidaUsuarioImpl implements ServicioPartidaUsuario {
    private RepositorioPartidaUsuario repositorioPartidaUsuario;

    @Autowired
    public ServicioPartidaUsuarioImpl(RepositorioPartidaUsuario repositorioPartidaUsuario) {
        this.repositorioPartidaUsuario = repositorioPartidaUsuario;
    }

    @Override
    public PartidaUsuario tenerPartidaUsuarioPorId(Long id) {
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorId(id);
    }
}
