package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.RepositorioPropiedad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("repositorioPropiedad")
public class RepositorioPropiedadImpl implements RepositorioPropiedad {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPropiedadImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void actualizar(Propiedad propiedad) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(propiedad);
    }

    @Override
    public List<Propiedad> obtenerTodasPropiedades() {
        final Session session = sessionFactory.getCurrentSession();
        return session.createCriteria(Propiedad.class).list();
    }

    @Override
    public Propiedad obtenerPropiedadPorNroCasillero(Integer nroCasiillero) {
        final Session session = sessionFactory.getCurrentSession();
        return (Propiedad) session.createCriteria(Propiedad.class)
                .add(Restrictions.eq("nroCasilla", nroCasiillero))
                .uniqueResult();
    }

    @Override
    public Propiedad obtenerPropiedadPorId(Long idPropiedad) {
        final Session session = sessionFactory.getCurrentSession();
        return (Propiedad) session.createCriteria(Propiedad.class)
                .add(Restrictions.eq("id", idPropiedad))
                .uniqueResult();
    }
}
