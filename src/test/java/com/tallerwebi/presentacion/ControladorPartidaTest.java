package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcesoDeJugadoresException;
import com.tallerwebi.infraestructura.ServicioPartidaImpl;
import org.dom4j.rule.Mode;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.*;

public class ControladorPartidaTest {
    private ControladorPartida controladorPartida;
    private ServicioPartida servicioPartida;

    @BeforeEach
    void init(){
        servicioPartida = mock(ServicioPartidaImpl.class);
        this.controladorPartida = new ControladorPartida(servicioPartida);
    }

    @Test
    public void siElUsuarioEstaLogeadoIrAPartida(){
        HttpSession session = mock(HttpSession.class);
        givenUsuarioExistente(session);
        ModelAndView mv  = whenIrALaVistaPartida(session);
        thenEstaEnLaVista(mv, "partidas.html");
    }

    private void givenUsuarioExistente(HttpSession session){
        Usuario usuario = new Usuario();
        session.setAttribute("usuarioLogeado", usuario);
        when(session.getAttribute("usuarioLogeado")).thenReturn(usuario);
    }

    private ModelAndView whenIrALaVistaPartida(HttpSession session){
        return this.controladorPartida.irPartida(session);
    }

    private void thenEstaEnLaVista(ModelAndView mv, String vista){
        assertThat(mv.getViewName(), equalToIgnoringCase(vista));
    }


    @Test
    public void siElUsuarioEstaLogeadoYTienePendienteUnaPartidaAbierta(){
        HttpSession session = mock(HttpSession.class);
        givenUsuarioExistente(session);
        when(servicioPartida.verSiTieneUnaPartidaEnCursoPorUsuario(
                        (Usuario) session.getAttribute("usuarioLogeado"))).thenReturn(givenPartidaUsuarioDePartidaABIERTA());

        ModelAndView mv  = whenIrALaVistaPartida(session);
        thenEstaEnLaVista(mv,"redirect:/espera");
    }
    private PartidaUsuario givenPartidaUsuarioDePartidaABIERTA(){
        Partida partida = new Partida();
        partida.setEstadoPartida(EstadoPartida.ABIERTA);
        PartidaUsuario partidaUsuario = new PartidaUsuario();
        partidaUsuario.setPartida(partida);
        return partidaUsuario;
    }


    @Test
    public void siElUsuarioEstaLogeadoYTienePendienteUnaPartidaCuso(){
        HttpSession session = mock(HttpSession.class);
        givenUsuarioExistente(session);
        when(servicioPartida.verSiTieneUnaPartidaEnCursoPorUsuario(
                (Usuario) session.getAttribute("usuarioLogeado"))).thenReturn(givenPartidaUsuarioDePartidaCURSO());

        ModelAndView mv  = whenIrALaVistaPartida(session);
        thenEstaEnLaVista(mv,"redirect:/monopoly/?id=1");
    }
    private PartidaUsuario givenPartidaUsuarioDePartidaCURSO(){
        Partida partida = new Partida();
        partida.setEstadoPartida(EstadoPartida.EN_CURSO);
        partida.setId(1L);
        PartidaUsuario partidaUsuario = new PartidaUsuario();
        partidaUsuario.setPartida(partida);
        partidaUsuario.setId(1L);
        return partidaUsuario;
    }



    @Test
    public void queElUsuarioPuedaEntrarAUnaPartida() throws ExcesoDeJugadoresException{
        HttpSession session = mock(HttpSession.class);
        givenUsuarioExistente(session);

        when(servicioPartida.obtenerPartidaPorPartidaId(1L)).thenReturn(new Partida());

        ModelAndView mv = whenUnirseAPartida(session);
        thenEstaEnLaVista(mv,"redirect:/espera");
    }

    private ModelAndView whenUnirseAPartida(HttpSession session) throws ExcesoDeJugadoresException {
        return this.controladorPartida.unirseAUnaPartida(1L, session);
    }


    @Test
    public void queElUsuarioNoPuedaEntrarAUnaPartidaPorExcesoDeJugadores() throws ExcesoDeJugadoresException {
        HttpSession session = mock(HttpSession.class);
        givenUsuarioExistente(session);

        when(servicioPartida.obtenerPartidaPorPartidaId(1L)).thenReturn(new Partida());

        doThrow(ExcesoDeJugadoresException.class)
                .when(servicioPartida)
                .unirseAPartida(new Partida(), new Usuario());

    }
    private ModelAndView whenUnirseAPartidaId(Long id, HttpSession session) throws ExcesoDeJugadoresException {
        return this.controladorPartida.unirseAUnaPartida(id, session);
    }

    @Test
    public void queElUsuarioVayaALaSalaEspera(){
        HttpSession session = mock(HttpSession.class);

        givenUsuarioExistente(session);
        when(session.getAttribute("partidaEnJuego")).thenReturn(new Partida());

        when(this.servicioPartida.verSiTieneUnaPartidaEnCursoPorUsuario((Usuario) session.getAttribute("usuarioLogeado")))
                .thenReturn(givenPartidaUsuarioDePartidaABIERTA());

        when(this.servicioPartida.verUsuariosEnlaPartidaEspera(1L))
                .thenReturn(givenUsuariosPartida());

        when(this.servicioPartida.obtenerCreadoUsuarioDeUnaPartida(1L))
                .thenReturn(new Usuario());

        ModelAndView mv = whenIrSalaEspera(session);

        thenEstaEnLaVista(mv, "sala_espera.html");
    }

    private List<Usuario> givenUsuariosPartida(){
        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(new Usuario());
        usuarios.add(new Usuario());
        return usuarios;
    }

    private ModelAndView whenIrSalaEspera(HttpSession session){
        return this.controladorPartida.irSalaEspera(session);
    }

    @Test
    public void queElUsuarioNoVayaALaSalaEspera(){
        HttpSession session = mock(HttpSession.class);

        givenUsuarioExistente(session);
        when(session.getAttribute("partidaEnJuego")).thenReturn(new Partida());

        when(this.servicioPartida.verSiTieneUnaPartidaEnCursoPorUsuario((Usuario) session.getAttribute("usuarioLogeado")))
                .thenReturn(givenPartidaUsuarioDePartidaCURSO());

        when(this.servicioPartida.verUsuariosEnlaPartidaEspera(1L))
                .thenReturn(givenUsuariosPartida());

        when(this.servicioPartida.obtenerCreadoUsuarioDeUnaPartida(1L))
                .thenReturn(new Usuario());

        ModelAndView mv = whenIrSalaEspera(session);

        thenEstaEnLaVista(mv, "redirect:/monopoly/?id=1");
    }

    @Test
    public void queElUsuarioSalgaDeLaPartida(){
        HttpSession session = mock(HttpSession.class);
        givenUsuarioExistente(session);

        ModelAndView mv = whenSalirDeLaPartida(session);

        thenEstaEnLaVista(mv,"redirect:/ir-menu");
    }
    private ModelAndView whenSalirDeLaPartida(HttpSession session){
        return this.controladorPartida.salirPartidaUsuario(1L,session);
    }
}
