package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.presentacion.ControladorPerfil;
import com.tallerwebi.presentacion.DatosPerfil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doThrow;


public class ControladorPerfilTest {

    @Mock
    private ServicioPerfil servicioPerfil;

    @InjectMocks
    private ControladorPerfil controladorPerfil;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testActualizarPerfil_ContraseniaInvalidaException() throws EmailInvalidoException, ContraseniaInvalidaException, CamposVaciosException {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setContraseniaNueva("password1");
        datosPerfil.setRepiteContraseniaNueva("password2");

        doThrow(ContraseniaInvalidaException.class).when(servicioPerfil).actualizarPerfil(datosPerfil);

        ModelAndView result = controladorPerfil.actualizarPerfil(datosPerfil);

        assertEquals("editarPerfil", result.getViewName());
        assertEquals("Las contraseñas no coinciden o tienen menos de 5 caracteres", result.getModel().get("error"));
    }

    @Test
    public void testActualizarPerfil_EmailInvalidoException() throws EmailInvalidoException, ContraseniaInvalidaException,CamposVaciosException {
        DatosPerfil datosPerfil = new DatosPerfil();
        datosPerfil.setEmail("invalid-email");

        doThrow(EmailInvalidoException.class).when(servicioPerfil).actualizarPerfil(datosPerfil);

        ModelAndView result = controladorPerfil.actualizarPerfil(datosPerfil);

        assertEquals("editarPerfil", result.getViewName());
        assertEquals("El email proporcionado no es válido", result.getModel().get("error"));
    }


    @Test
    public void testActualizarPerfil_OtroError() throws EmailInvalidoException, ContraseniaInvalidaException, CamposVaciosException {
        DatosPerfil datosPerfil = new DatosPerfil();

        // Simulando otro tipo de excepción que no sea ContraseniaInvalidaException ni EmailInvalidoException
        doThrow(RuntimeException.class).when(servicioPerfil).actualizarPerfil(datosPerfil);

        ModelAndView result = controladorPerfil.actualizarPerfil(datosPerfil);

        assertEquals("editarPerfil", result.getViewName());
        assertEquals("Error al actualizar el perfil", result.getModel().get("error"));
    }
}
