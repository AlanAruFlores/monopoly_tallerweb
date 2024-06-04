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
import org.springframework.web.multipart.MultipartFile;

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
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseniaInvalidaException, EmailInvalidoException, CamposVaciosException {

        // Validamos que la contraseña actual y el email no estén vacíos
        if(datosPerfil.getContraseniaActual().isEmpty() || datosPerfil.getEmail().isEmpty()) {
            throw new CamposVaciosException();
        }

        // Buscar el usuario actual (suponiendo que el ID del usuario sea conocido y fijo en este ejemplo)
        Usuario usuarioActual = this.repositorioUsuario.buscarUsuarioPorId(2L);

        // Validar la contraseña actual
        if (!usuarioActual.getPassword().equals(datosPerfil.getContraseniaActual())) {
            throw new ContraseniaInvalidaException();
        }

        // Validar si la nueva contraseña coincide con la repetición, solo si ambas no están vacías
        if (!datosPerfil.getContraseniaNueva().isEmpty() || !datosPerfil.getRepiteContraseniaNueva().isEmpty()) {
            if (!datosPerfil.getContraseniaNueva().equals(datosPerfil.getRepiteContraseniaNueva())) {
                throw new ContraseniaInvalidaException();
            }

            // Validar si la contraseña nueva tiene más de 5 caracteres
            if (datosPerfil.getContraseniaNueva().length() < 5) {
                throw new ContraseniaInvalidaException();
            }
        }

        // Validar el formato del email
        if (!datosPerfil.getEmail().contains("@") || !datosPerfil.getEmail().contains(".com")) {
            throw new EmailInvalidoException();
        }

        // Actualizar solo los campos que no están vacíos
        if (!datosPerfil.getNombre().isEmpty()) {
            usuarioActual.setNombreUsuario(datosPerfil.getNombre());
        }
        usuarioActual.setEmail(datosPerfil.getEmail());

        if (!datosPerfil.getContraseniaNueva().isEmpty()) {
            usuarioActual.setPassword(datosPerfil.getContraseniaNueva());
            usuarioActual.setRepitePassword(datosPerfil.getRepiteContraseniaNueva());
        }

        // Guardar los cambios en el repositorio
        this.repositorioUsuario.actualizarUsuario(usuarioActual);
    }


    @Override
    public Usuario devolverUsuario(Long id){
        return this.repositorioUsuario.buscarUsuarioPorId(id);
    }
}
