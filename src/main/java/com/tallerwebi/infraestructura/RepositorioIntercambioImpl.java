package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Intercambio;
import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.RepositorioIntercambio;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioIntercambio")
public class RepositorioIntercambioImpl implements RepositorioIntercambio {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioIntercambioImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearIntercambio(Intercambio intercambio) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(intercambio);
    }

    @Override
    public Intercambio getIntercambioPorId(Long id) {
        final Session session  = sessionFactory.getCurrentSession();
        return (Intercambio) session.createCriteria(Intercambio.class)
                .add(Restrictions.eq("id", id)).uniqueResult();
    }

    @Override
    public void actualizarIntercambio(Intercambio intercambio) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(intercambio);
    }

    @Override
    public Intercambio buscarIntercambioByEmisorId(PartidaUsuario partidaUsuario) {
        final Session session  = sessionFactory.getCurrentSession();
        return (Intercambio)  session.createCriteria(Intercambio.class)
                .createAlias("emisor", "e")
                .add(Restrictions.eq("e.id",partidaUsuario.getId()))
                .uniqueResult();
    }

    @Override
    public Intercambio buscarIntercambioByDestinatarioId(PartidaUsuario partidaUsuario) {
        final Session session = sessionFactory.getCurrentSession();

        return (Intercambio) session.createCriteria(Intercambio.class)
                .createAlias("receptor", "r")
                .add(Restrictions.eq("r.id",partidaUsuario.getId()))
                .uniqueResult();

    }

    @Override
    public Intercambio buscarIntercambioByEmisorIdAndDestinatarioId(Long idEmisor, Long idReceptor){
        final Session session = sessionFactory.getCurrentSession();

        return (Intercambio) session.createCriteria(Intercambio.class)
                .createAlias("emisor", "e")
                .createAlias("receptor", "r")
                .add(Restrictions.eq("e.id",idEmisor))
                .add(Restrictions.eq("r.id",idReceptor))
                .uniqueResult();
    }

    @Override
    public void eliminarIntercambioPorId(Long id) {
        final Session session = sessionFactory.getCurrentSession();
        String queryEliminar = "CALL eliminar_intercambio(:intercambioId)";
        session.createSQLQuery(queryEliminar)
                .setParameter("intercambioId", id)
                .executeUpdate();
    }
}
