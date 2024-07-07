package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.PartidaUsuarioPropiedad;
import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.RepositorioPartidaUsuarioPropiedad;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
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

    @Override
    public void eliminarPartidaUsuarioPropiedadPorJugadorYPropiedad(PartidaUsuario pu, Propiedad p){
        final Session session = sessionFactory.getCurrentSession();
        System.out.println("SESSION: "+session);

        session.createQuery("DELETE FROM PartidaUsuarioPropiedad  WHERE partidaUsuario.id = :idJugador and propiedad.id = :idPropiedad")
                .setParameter("idJugador", pu.getId())
                .setParameter("idPropiedad", p.getId())
                .executeUpdate();
    }

    @Override
    public PartidaUsuarioPropiedad obtenerPartidaUsuarioPropiedadPorId(Integer id){
        final Session session = sessionFactory.getCurrentSession();
        return (PartidaUsuarioPropiedad) session.createCriteria(PartidaUsuarioPropiedad.class)
                .add(Restrictions.eq("id", id))
                .uniqueResult();
    }

    @Override
    public void actualizarPartidaUsuarioPropiedad(PartidaUsuarioPropiedad pup) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(pup);
    }

}
