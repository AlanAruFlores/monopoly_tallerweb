package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.RepositorioPartida;
import com.tallerwebi.dominio.Usuario;
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
        partidaPersistida.setEstadoPartida(partida.getEstadoPartida());
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

    @Override
    public Partida obtenerPartidaPorCreador(Usuario creador) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (Partida) session.createCriteria(Partida.class)
                .createAlias("creador","c")
                .add(Restrictions.eq("c.id",creador.getId()))
                .uniqueResult();
    }

    @Override
    public void cambiarEstadoDeLaPartida(Partida partida){
        final Session session = this.sessionFactory.getCurrentSession();
        //Partida partidaPersistida = session.get(Partida.class, partida.getId());
        //partidaPersistida.setEstadoPartida(partida.getEstadoPartida());
        session.update(partida);
    }

    @Override
    public void eliminarPartidaPorId(Long partidaId) {
        final Session session  = this.sessionFactory.getCurrentSession();
        session.createQuery("DELETE FROM Partida where id = :partidaId")
                .setParameter("partidaId", partidaId)
                .executeUpdate();
    }
}
