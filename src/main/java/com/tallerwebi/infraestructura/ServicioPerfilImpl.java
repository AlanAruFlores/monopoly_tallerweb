package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;
import com.tallerwebi.presentacion.DatosPerfil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioPerfilImpl implements ServicioPerfil {

    private RepositorioUsuario repositorioUsuario;


    @Autowired
    public ServicioPerfilImpl(RepositorioUsuario repositorioUsuario) {
        this.repositorioUsuario = repositorioUsuario;
    }


    @Override
    public void actualizarPerfil(DatosPerfil datosPerfil, HttpSession session) throws ContraseniaInvalidaException, EmailInvalidoException, CamposVaciosException {
        Usuario usuarioActual = (Usuario) session.getAttribute("usuarioActual");
        if (usuarioActual == null) {
            throw new RuntimeException("Usuario no autenticado");
        }

        if (datosPerfil.getContraseniaActual().isEmpty() || datosPerfil.getEmail().isEmpty()) {
            throw new CamposVaciosException();
        }

        if (!usuarioActual.getPassword().equals(datosPerfil.getContraseniaActual())) {
            throw new ContraseniaInvalidaException();
        }

        if (!datosPerfil.getContraseniaNueva().isEmpty() || !datosPerfil.getRepiteContraseniaNueva().isEmpty()) {
            if (!datosPerfil.getContraseniaNueva().equals(datosPerfil.getRepiteContraseniaNueva())) {
                throw new ContraseniaInvalidaException();
            }

            if (datosPerfil.getContraseniaNueva().length() < 5) {
                throw new ContraseniaInvalidaException();
            }
        }

        if (!datosPerfil.getEmail().contains("@") || !datosPerfil.getEmail().contains(".com")) {
            throw new EmailInvalidoException();
        }

        if (!datosPerfil.getNombre().isEmpty()) {
            usuarioActual.setNombre(datosPerfil.getNombre());
        }

        if (!datosPerfil.getNombreUsuario().isEmpty()) {
            usuarioActual.setNombre(datosPerfil.getNombreUsuario());
        }
        usuarioActual.setEmail(datosPerfil.getEmail());

        if (!datosPerfil.getContraseniaNueva().isEmpty()) {
            usuarioActual.setPassword(datosPerfil.getContraseniaNueva());
            usuarioActual.setRepitePassword(datosPerfil.getContraseniaNueva());
        }

        if (datosPerfil.getImagen() != null) {
            usuarioActual.setImagenPerfil(datosPerfil.getImagen());
        }

        this.repositorioUsuario.actualizarUsuario(usuarioActual);
    }





    @Override
    public Usuario devolverUsuario(Long id){
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }
}
