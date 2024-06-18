package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.RepositorioPartidaUsuario;
import com.tallerwebi.dominio.ServicioMenu;
import com.tallerwebi.dominio.Usuario;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service("servicioMenu")
@Transactional
public class ServicioMenuImpl implements ServicioMenu {
    private RepositorioPartidaUsuario repositorioPartidaUsuario;

    public ServicioMenuImpl(RepositorioPartidaUsuario repositorioPartidaUsuario){
        this.repositorioPartidaUsuario  = repositorioPartidaUsuario;
    }

    @Override
    public PartidaUsuario verSiTieneUnaPartidaEnCursoPorUsuario(Usuario usuarioActual){
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorUsuarioId(usuarioActual.getId());
    }

}
