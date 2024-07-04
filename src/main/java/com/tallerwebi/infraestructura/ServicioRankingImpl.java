package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioRanking;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class ServicioRankingImpl implements ServicioRanking {

    private RepositorioUsuario repositorioUsuario;

    @Autowired
    public ServicioRankingImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    @Override
    public List<Usuario> findAllSortedByVictorias() {
        return this.repositorioUsuario.mostrarUsuariosPorVictorias();
    }
}