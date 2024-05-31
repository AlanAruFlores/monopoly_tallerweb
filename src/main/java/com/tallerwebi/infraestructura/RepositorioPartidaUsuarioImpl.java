package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.RepositorioPartidaUsuario;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPartidaUsuario")
public class RepositorioPartidaUsuarioImpl implements RepositorioPartidaUsuario {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidaUsuarioImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearPartidaUsuario(PartidaUsuario partidaUsuario) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(partidaUsuario);
    }
    @Override
    public List<Usuario> obtenerUsuariosEnUnaPartida(Long partidaId) {
        final Session session = this.sessionFactory.getCurrentSession();
        /*Con esto obtengo los registros y su cantidad*/
        return session.createCriteria(PartidaUsuario.class)
                .createAlias("partida", "p")
                .add(Restrictions.eq("p.id", partidaId))
                .setProjection(Projections.property("usuario"))
                .list();
    }


    @Override
    public void actualizarPartidaUsuario() {

    }

    @Override
    public void eliminarPartidaUsuario() {

    }
}
