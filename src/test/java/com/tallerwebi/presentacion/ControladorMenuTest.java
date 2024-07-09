package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMenu;
import com.tallerwebi.dominio.ServicioMonopoly;
import com.tallerwebi.dominio.Usuario;
import static org.hamcrest.MatcherAssert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

import static org.hamcrest.Matchers.equalToIgnoringCase;
import static org.mockito.Mockito.mock;

public class ControladorMenuTest {
    private MenuControlador controladorMenu;
    private ServicioMenu servicioMenu;

    @BeforeEach
    void init(){
        servicioMenu = mock(ServicioMenu.class);
        controladorMenu = new MenuControlador(servicioMenu);
    }


    public void givenUsuarioLogeado(HttpSession session){
        session.setAttribute("usuarioLogeado", new Usuario());
    }


    @Test
    public void siElUsuarioEstaLogeadoIrAlMenu(){
        //given
        HttpSession session = mock(HttpSession.class);
        givenUsuarioLogeado(session);
        //when
        ModelAndView mv = whenIrAlMenu(session);
        //then
        thenEstaEnLaVista(mv, "menu.html");
    }

    private ModelAndView whenIrAlMenu(HttpSession session) {
        return controladorMenu.irAlMenu(session);
    }

    public void thenEstaEnLaVista(ModelAndView mv,String nombreVista) {
        assertThat(mv.getViewName(),equalToIgnoringCase(nombreVista));
    }


    @Test
    public void siElUsuarioEstaLogeadoIrAJugar(){
        HttpSession session = mock(HttpSession.class);
        givenUsuarioLogeado(session);
        ModelAndView mv = whenIrAJugar();
        thenEstaEnLaVista(mv,"redirect:/monopoly");

    }

    private ModelAndView whenIrAJugar() {
        return controladorMenu.irAJugar();
    }

    @Test
    public void siEstaLogeadoIrASalir(){
        HttpSession session = mock(HttpSession.class);
        givenUsuarioLogeado(session);
        ModelAndView mv  = whenIrASalir(session);
        thenEstaEnLaVista(mv, "redirect:/login");
    }

    public ModelAndView whenIrASalir(HttpSession session){
        return  controladorMenu.irASalir(session);
    }



}







