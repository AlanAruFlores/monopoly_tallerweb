package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.presentacion.DatosPerfil;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.Objects;

@Service
@Transactional
public class ServicioPerfilImpl implements ServicioPerfil {


    @Override
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseniaInvalidaException, EmailInvalidoException {
        // Validar si la nueva contraseña coincide con la repetición
        if (datosPerfil.getContraseniaNueva() != null && datosPerfil.getRepiteContraseniaNueva() != null && !datosPerfil.getRepiteContraseniaNueva().equals(datosPerfil.getRepiteContraseniaNueva())) {
            throw new ContraseniaInvalidaException("Las contraseñas no coinciden");
        }
        // Validar el formato del email
        if (datosPerfil.getEmail() != null && !datosPerfil.getEmail().contains("@") && !datosPerfil.getEmail().contains(".com")) {
            throw new EmailInvalidoException("El email proporcionado no es válido");
        }

    }





}
