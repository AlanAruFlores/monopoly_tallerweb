package com.tallerwebi.dominio;

import com.tallerwebi.dominio.RepositorioUsuario;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;
import com.tallerwebi.infraestructura.ServicioPerfilImpl;
import com.tallerwebi.presentacion.DatosPerfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.servlet.http.HttpSession;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


public class ServicioPerfilImplTest {

    @Mock
    private RepositorioUsuario repositorioUsuario;

    @Mock
    private HttpSession session;

    @InjectMocks
    private ServicioPerfilImpl servicioPerfil;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void actualizarPerfil_ContraseniaNuevaCorta_DebeLanzarExcepcion() {
        Usuario usuarioActual = new Usuario();
        usuarioActual.setPassword("contraseniaActual");

        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setContraseniaActual("contraseniaActual");
        datosPerfil.setEmail("emailValido");
        datosPerfil.setContraseniaNueva("corta");

        when(session.getAttribute("usuarioActual")).thenReturn(usuarioActual);

        assertThrows(ContraseniaInvalidaException.class, () -> servicioPerfil.actualizarPerfil(datosPerfil, session));
    }

    @Test
    public void actualizarPerfil_ContraseniaNuevaDiferenteDeRepite_DebeLanzarExcepcion() {
        Usuario usuarioActual = new Usuario();
        usuarioActual.setPassword("contraseniaActual");

        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setContraseniaActual("contraseniaActual");
        datosPerfil.setEmail("emailValido");
        datosPerfil.setContraseniaNueva("nuevaContrasenia");
        datosPerfil.setRepiteContraseniaNueva("diferenteContrasenia");

        when(session.getAttribute("usuarioActual")).thenReturn(usuarioActual);

        assertThrows(ContraseniaInvalidaException.class, () -> servicioPerfil.actualizarPerfil(datosPerfil, session));
    }

    @Test
    public void actualizarPerfil_CamposVacios_DebeLanzarExcepcion() {
        Usuario usuarioActual = new Usuario();
        usuarioActual.setPassword("contraseniaActual");

        DatosPerfil datosPerfil = new DatosPerfil();
        // campos vacíos
        datosPerfil.setContraseniaActual("");
        datosPerfil.setEmail("");

        when(session.getAttribute("usuarioActual")).thenReturn(usuarioActual);

        assertThrows(CamposVaciosException.class, () -> servicioPerfil.actualizarPerfil(datosPerfil, session));
    }


}
