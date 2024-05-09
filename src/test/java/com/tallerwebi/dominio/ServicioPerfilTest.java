package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.infraestructura.ServicioPerfilImpl;
import com.tallerwebi.presentacion.DatosPerfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioPerfilTest {


    private ServicioPerfil servicioPerfil = new ServicioPerfilImpl();

    @Test
    public void actualizarPerfil_ContraseniaInvalidaException() {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setContraseniaNueva("password1");
        datosPerfil.setRepiteContraseniaNueva("password2");

        assertThrows(ContraseniaInvalidaException.class, () -> {
            servicioPerfil.actualizarPerfil(datosPerfil);
        });
    }

    @Test
    public void actualizarPerfil_EmailInvalidoException() {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setContraseniaNueva("password");
        datosPerfil.setRepiteContraseniaNueva("password");
        datosPerfil.setEmail("emailincorrecto");

        assertThrows(EmailInvalidoException.class, () -> {
            servicioPerfil.actualizarPerfil(datosPerfil);
        });
    }

    @Test
    public void actualizarPerfil_SinExcepciones() throws EmailInvalidoException, ContraseniaInvalidaException {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setContraseniaNueva("password");
        datosPerfil.setRepiteContraseniaNueva("password");
        datosPerfil.setEmail("usuario@example.com");

        // Probamos que el método no lance ninguna excepción
        servicioPerfil.actualizarPerfil(datosPerfil);
    }
}
