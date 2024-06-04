package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuarioPropiedad;
import com.tallerwebi.dominio.RepositorioPartidaUsuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("repositorioPartidaUsuarioPropiedad")
public class RepositorioPartidaUsuarioPropiedad  implements com.tallerwebi.dominio.RepositorioPartidaUsuarioPropiedad {
    private SessionFactory sessionFactory;

    @Autowired
    public RepositorioPartidaUsuarioPropiedad(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
    }

    public void crearPartidaUsuarioPropiedad(PartidaUsuarioPropiedad pup){
        final Session session = sessionFactory.getCurrentSession();
        session.save(pup);
    }

}
