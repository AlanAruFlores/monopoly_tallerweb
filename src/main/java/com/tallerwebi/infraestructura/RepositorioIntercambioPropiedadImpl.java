package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Intercambio;
import com.tallerwebi.dominio.IntercambioPropiedades;
import com.tallerwebi.dominio.RepositorioIntercambioPropiedad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RepositorioIntercambioPropiedadImpl implements RepositorioIntercambioPropiedad {
    private SessionFactory sessionFactory;


    @Autowired
    public RepositorioIntercambioPropiedadImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }


    @Override
    public void crearIntercambioPropiedad(IntercambioPropiedades intercambioPropiedades) {
        final Session session  = this.sessionFactory.getCurrentSession();
        session.save(intercambioPropiedades);
    }

    @Override
    public List<IntercambioPropiedades> obtenerIntercambioPropiedadesPorIntercambio(Intercambio intercambio) {
        final Session session = this.sessionFactory.getCurrentSession();
        return (List<IntercambioPropiedades>) session.createCriteria(IntercambioPropiedades.class)
                .createAlias("intercambio", "i")
                .add(Restrictions.eq("i.id",intercambio.getId()))
                .list();

    }
}
