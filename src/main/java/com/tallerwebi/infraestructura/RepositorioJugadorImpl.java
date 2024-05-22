package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.RepositorioJugador;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioJugador")
public class RepositorioJugadorImpl implements RepositorioJugador {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioJugadorImpl(SessionFactory sessionFactory) {
        this.sessionFactory=sessionFactory;
    }

    @Override
    public void guardar(Jugador jugador) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(jugador);
    }

    @Override
    public Jugador obtenerJugador(Long usuarioId) {
        final Session session = sessionFactory.getCurrentSession();
        return (Jugador) session.createCriteria(Jugador.class)
                .createAlias("usuario","u")
                .add(Restrictions.eq("u.id", usuarioId))
                .uniqueResult();
    }

    @Override
    public void actualizar(Jugador jugador) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(jugador);
    }
}
