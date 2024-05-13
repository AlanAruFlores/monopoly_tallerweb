package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;
import com.tallerwebi.presentacion.DatosPerfil;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;

@Service
@Transactional
public class ServicioPerfilImpl implements ServicioPerfil {


    @Override
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseniaInvalidaException, EmailInvalidoException, CamposVaciosException {

        //Validamos que ningun campo este Vacio
        if(datosPerfil.getNombre().isEmpty() || datosPerfil.getEmail().isEmpty() || datosPerfil.getContraseniaActual().isEmpty() ||
                datosPerfil.getContraseniaNueva().isEmpty() || datosPerfil.getRepiteContraseniaNueva().isEmpty()){
            throw new CamposVaciosException();
        }

        // Validar si la nueva contraseña coincide con la repetición
        if (datosPerfil.getContraseniaNueva() != null && datosPerfil.getRepiteContraseniaNueva() != null &&
                !datosPerfil.getContraseniaNueva().equals(datosPerfil.getRepiteContraseniaNueva())) {
            throw new ContraseniaInvalidaException();
        }

        //validar si la contraseña tiene mas de 5 caracteres
        if(datosPerfil.getContraseniaNueva().length() < 5){
            throw new ContraseniaInvalidaException();
        }

        // Validar el formato del email
        if (datosPerfil.getEmail() != null && !datosPerfil.getEmail().contains("@") && !datosPerfil.getEmail().contains(".com")) {
            throw new EmailInvalidoException();
        }


    }
}
