package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuarioPropiedad;
import com.tallerwebi.dominio.RepositorioPartidaUsuarioPropiedad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPartidaUsuarioPropiedad")
public class RepositorioPartidaUsuarioPropiedadImpl implements RepositorioPartidaUsuarioPropiedad {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidaUsuarioPropiedadImpl(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void crearPartidaUsuarioPropiedad(PartidaUsuarioPropiedad pup){
        final Session session = sessionFactory.getCurrentSession();
        session.save(pup);
    }

}
