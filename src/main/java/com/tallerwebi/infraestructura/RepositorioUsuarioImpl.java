package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.infraestructura.RepositorioUsuarioImpl;
import com.tallerwebi.dominio.Usuario;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository ("repositorioUsuario")
public class RepositorioUsuarioImpl implements RepositorioUsuario {

    private SessionFactory sessionFactory;

   @Autowired
   public RepositorioUsuarioImpl (SessionFactory sessionFactory) {
       this.sessionFactory = sessionFactory;

   }

    @Override
    public void guardar(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        session.save(usuario);
    }



}
