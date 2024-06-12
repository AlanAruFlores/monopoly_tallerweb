package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
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

    // Verifico si el mail ya está en uso en la bdd
    @Override
    public Boolean existeUsuarioConEmail(String email) {
        try (Session session = sessionFactory.openSession()) {
            // Creamos la consulta para verificar si hay algún usuario con el mismo correo electrónico
            String sql = "SELECT COUNT(u) FROM Usuario u WHERE u.email = :email";
            Long count = (Long) session.createQuery(sql).setParameter("email", email).uniqueResult();
            // Si count es mayor que 0, significa que hay al menos un usuario con el mismo correo electrónico
            return count != null && count > 0;
        } catch (Exception e) {
            // Manejo de excepciones si ocurre algún error en la consulta
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public void actualizarUsuario(Usuario usuario) {
        final Session session = sessionFactory.getCurrentSession();
        session.update(usuario);
    }

    @Override
    public Usuario buscarUsuarioPorId(Long id) {
        Session session = sessionFactory.getCurrentSession();
        return session.get(Usuario.class, id);
    }

}
