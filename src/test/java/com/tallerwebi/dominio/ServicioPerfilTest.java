package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.CamposVaciosException;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.infraestructura.ServicioPerfilImpl;
import com.tallerwebi.presentacion.DatosPerfil;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ServicioPerfilTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    private ServicioPerfil servicioPerfil = new ServicioPerfilImpl(repositorioUsuario);

    @Test
    public void actualizarPerfil_ContraseniaInvalidaException() {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setNombre("usuario");
        datosPerfil.setEmail("usuario@gmail.com");
        datosPerfil.setContraseniaActual("passwordActual");
        datosPerfil.setContraseniaNueva("password1");
        datosPerfil.setRepiteContraseniaNueva("password2");
        datosPerfil.setImagen("hola.jpg");

        assertThrows(ContraseniaInvalidaException.class, () -> {
            servicioPerfil.actualizarPerfil(datosPerfil);
        });
    }

    @Test
    public void actualizarPerfil_EmailInvalidoException() {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setNombre("usuario");
        datosPerfil.setEmail("usuario@gmail.com");
        datosPerfil.setContraseniaActual("passwordActual");
        datosPerfil.setContraseniaNueva("password");
        datosPerfil.setRepiteContraseniaNueva("password");
        datosPerfil.setEmail("emailincorrecto");

        assertThrows(EmailInvalidoException.class, () -> {
            servicioPerfil.actualizarPerfil(datosPerfil);
        });
    }

    @Test
    public void actualizarPerfil_SinExcepciones() throws EmailInvalidoException, ContraseniaInvalidaException, CamposVaciosException {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setNombre("usuario");
        datosPerfil.setEmail("usuario@gmail.com");
        datosPerfil.setContraseniaActual("passwordActual");
        datosPerfil.setContraseniaNueva("password");
        datosPerfil.setRepiteContraseniaNueva("password");
        datosPerfil.setEmail("usuario@example.com");

        // Probamos que el método no lance ninguna excepción
        servicioPerfil.actualizarPerfil(datosPerfil);
    }
}
