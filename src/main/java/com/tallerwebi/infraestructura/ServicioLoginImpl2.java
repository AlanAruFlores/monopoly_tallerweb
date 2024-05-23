package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioLogin2;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service ("servicioLogin")
@Transactional
public class ServicioLoginImpl2 implements ServicioLogin2 {
    private RepositorioUsuario repositorioUsuario;
    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    public ServicioLoginImpl2(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }

    // Método consultar usuario para verificar el Login
    @Override
    public Usuario consultarUsuario(String email, String password) {
        // Crear una consulta para buscar el usuario por email y contraseña
        Query<Usuario> query = this.sessionFactory.getCurrentSession().createQuery(
                "FROM Usuario WHERE email = :email AND password = :password", Usuario.class);
        query.setParameter("email", email);
        query.setParameter("password", password);

        // Obtener el resultado de la consulta y verificar si se encontró algún usuario
        List<Usuario> usuarios = query.getResultList();
        if (!usuarios.isEmpty()) {
            return usuarios.get(0); // Devolver el primer usuario encontrado si hay
        } else {
            return null; // No se encontró ningún usuario
        }
    }

    // Método registrar para validar el Login
    @Override
    public void registrar(Usuario usuario) throws CamposIncompletosException,
            LongitudContraseñaException, MayusculaNumeroException, MailRegistradoException,
            ContraseñasNoCoincidenException, EmailInvalidoException {
        // Verificar que todos los campos hayan sido completados
        if (usuario.getNombre() == null || usuario.getApellido() == null ||
                usuario.getEmail() == null || usuario.getNombreUsuario() == null ||
                usuario.getPassword() == null || usuario.getRepitePassword() == null) {
            throw new CamposIncompletosException();
        }

        // Verificar longitud de contraseña
        if (usuario.getPassword().length() < 5) {
            throw new LongitudContraseñaException();
        }

        // Verificar mayúscula y número en la contraseña
        if (!usuario.getPassword().matches(".*[A-Z].*") || !usuario.getPassword().matches(".*\\d.*")) {
            throw new MayusculaNumeroException();
        }

        // Verificar terminación de correo electrónico
        if (!usuario.getEmail().endsWith(".com")) {
            throw new EmailInvalidoException();
        }

        // Verificar si el correo ya está en uso
        if (this.repositorioUsuario.existeUsuarioConEmail(usuario.getEmail())) {
            throw new MailRegistradoException();
        }

        // Verificar que las contraseñas coincidan
        if (!usuario.getPassword().equals(usuario.getRepitePassword())) {
            throw new ContraseñasNoCoincidenException();
        }
    }

    @Override
    public void agregarUsuario(Usuario dtoUsuario) {
       // usuarios.add(usuario);
        Usuario usarioNuevo = new Usuario(dtoUsuario.getNombre(), dtoUsuario.getEmail(),dtoUsuario.getPassword(),dtoUsuario.getApellido(),dtoUsuario.getNombreUsuario(), dtoUsuario.getRepitePassword());
        this.repositorioUsuario.guardar(usarioNuevo);
    }
}
