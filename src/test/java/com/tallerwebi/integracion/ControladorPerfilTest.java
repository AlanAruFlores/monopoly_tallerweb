package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.Usuario;
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

import javax.servlet.http.HttpSession;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
    public void testIrAlPerfil() {
        // Mock de HttpSession
        HttpSession session = mock(HttpSession.class);
        // Usuario de prueba
        Usuario usuarioActual = new Usuario();
        usuarioActual.setNombre("NombreUsuario");
        usuarioActual.setId(1L);
        usuarioActual.setVictorias(10);

        // Mock de sesión
        when(session.getAttribute("usuarioActual")).thenReturn(usuarioActual);

        // Llamada al método a probar
        ModelAndView modelAndView = controladorPerfil.irAlperfil(session);

        // Verificaciones
        assertEquals("perfil", modelAndView.getViewName());
        assertEquals("NombreUsuario", modelAndView.getModel().get("nombre"));
        assertEquals(1L, modelAndView.getModel().get("id"));
        assertEquals(10, modelAndView.getModel().get("victorias"));
    }

    @Test
    public void testEditarPerfil() {
        // Mock de HttpSession
        HttpSession session = mock(HttpSession.class);
        // Usuario de prueba
        Usuario usuarioActual = new Usuario();
        usuarioActual.setId(1L);
        // Mock de sesión
        when(session.getAttribute("usuarioActual")).thenReturn(usuarioActual);
        // Usuario devuelto por el servicio
        Usuario usuario = new Usuario();
        usuario.setNombre("NombreUsuario");
        usuario.setEmail("usuario@dominio.com");
        when(servicioPerfil.devolverUsuario(1L)).thenReturn(usuario);

        // Llamada al método a probar
        ModelAndView modelAndView = controladorPerfil.editarPerfil(session);

        // Verificaciones
        assertEquals("editarPerfil", modelAndView.getViewName());
        DatosPerfil datosPerfil = (DatosPerfil) modelAndView.getModel().get("datosPerfil");
        assertEquals("NombreUsuario", datosPerfil.getNombre());
        assertEquals("usuario@dominio.com", datosPerfil.getEmail());
    }


}

