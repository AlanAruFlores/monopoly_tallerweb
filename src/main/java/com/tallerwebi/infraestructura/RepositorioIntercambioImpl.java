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
    public Intercambio buscarIntercambioByDestinatarioId(PartidaUsuario partidaUsuario) {
        final Session session = sessionFactory.getCurrentSession();

        return (Intercambio) session.createCriteria(Intercambio.class)
                .createAlias("receptor", "r")
                .add(Restrictions.eq("r.id",partidaUsuario.getId()))
                .uniqueResult();

    }
}
