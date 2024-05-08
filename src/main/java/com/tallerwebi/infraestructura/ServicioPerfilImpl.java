package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.excepcion.ContraseñaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.presentacion.DatosPerfil;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioPerfilImpl implements ServicioPerfil {


    @Override
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseñaInvalidaException, EmailInvalidoException {
        // Validar si la nueva contraseña coincide con la repetición
        if (datosPerfil.getRepiteContraseniaNueva() != null && !datosPerfil.getContraseniaNueva().equals(datosPerfil.getRepiteContraseniaNueva())) {
            throw new ContraseñaInvalidaException("Las contraseñas no coinciden");
        }

        // Validar el formato del email
        if (datosPerfil.getEmail() != null && (!datosPerfil.getEmail().contains("@") || !datosPerfil.getEmail().contains(".com"))) {
            throw new EmailInvalidoException("El formato del email es inválido");
        }

    }
}
