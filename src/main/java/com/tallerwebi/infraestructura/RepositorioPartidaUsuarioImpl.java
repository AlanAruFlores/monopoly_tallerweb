package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
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
    public List<PartidaUsuario> obtenerPartidasUsuariosEnlaPartidaId(Long partidaId) {
        final Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(PartidaUsuario.class)
                .createAlias("partida", "p")
                .add(Restrictions.eq("p.id", partidaId))
                .list();
    }

    @Override
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (PartidaUsuario) session.createCriteria(PartidaUsuario.class)
                .createAlias("partida", "p")
                .createAlias("usuario","u")
                .add(Restrictions.eq("p.id", partidaId))
                .add(Restrictions.eq("u.id",usuarioId))
                .uniqueResult();
    }

    @Override
    public List<Color> obtenerColoresJugadoresUsuados(Long partidaId) {
        final Session session = this.sessionFactory.getCurrentSession();
        return session.createCriteria(PartidaUsuario.class)
                .createAlias("partida", "p")
                .add(Restrictions.eq("p.id", partidaId))
                .setProjection(Projections.property("colorUsuario"))
                .list();
    }


    @Override
    public void actualizarPartidaUsuario(PartidaUsuario partidaUsuario) {
        final Session session = this.sessionFactory.getCurrentSession();
        PartidaUsuario partidaUsuarioPersistida = session.get(PartidaUsuario.class,partidaUsuario.getId());
        partidaUsuarioPersistida.setPosicionCasilla(partidaUsuario.getPosicionCasilla());
        partidaUsuarioPersistida.setSaldo(partidaUsuario.getSaldo());
        session.update(partidaUsuarioPersistida);
    }

    @Override
    public void eliminarPartidaUsuario() {

    }

    @Override
    public void eliminarPartidaUsuarioPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId) {
        final Session session = this.sessionFactory.getCurrentSession();
        String queryEliminar = "DELETE FROM PartidaUsuario pu WHERE pu.partida.id = :partidaId AND pu.usuario.id = :usuarioId";

        session.createQuery(queryEliminar)
                .setParameter("partidaId", partidaId)
                .setParameter("usuarioId", usuarioId)
                .executeUpdate();
    }
}
