package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.RepositorioPartida;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPartida")
public class RepositorioPartidaImpl implements RepositorioPartida {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidaImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearPartida(Partida partida) {
        final Session session = this.sessionFactory.getCurrentSession();
        session.save(partida);
    }

    @Override
    public void actualizarPartida(Partida partida) {
        final Session session = this.sessionFactory.getCurrentSession();
        Partida partidaPersistida = session.get(Partida.class, partida.getId());
        partidaPersistida.setTurnoJugador(partida.getTurnoJugador());
        session.update(partidaPersistida);
    }

    @Override
    public List<Partida> obtenerPartidas() {
        final Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(Partida.class).list();
    }

    @Override
    public Partida obtenerPartidaPorId(Long partidaId) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (Partida) session.createCriteria(Partida.class)
                .add(Restrictions.eq("id",partidaId))
                .uniqueResult();
    }
}