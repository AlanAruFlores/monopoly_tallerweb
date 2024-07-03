package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.RepositorioIntercambioImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RepositorioIntercambioPropiedadImpl implements RepositorioIntercambioPropiedad{
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
}
