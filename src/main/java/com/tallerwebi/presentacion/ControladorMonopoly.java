package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorMonopoly {

    ServicioMonopoly servicioMonopoly;

    @Autowired
    public ControladorMonopoly(ServicioMonopoly servicioMonopoly){
        this.servicioMonopoly = servicioMonopoly;
    }

    /*Ir al inicio del monopoly*/
    @RequestMapping("/monopoly")
    public ModelAndView irAlMonopoly(@RequestParam("id") Long partidaId, HttpSession session) throws Exception {
        ModelMap mp  = new ModelMap();

        List<PartidaUsuario> usuariosJugando = this.servicioMonopoly.obtenerTodosLosUsuariosJugandoEnLaPartidaId(partidaId);
        PartidaUsuario usuarioActual = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(partidaId, ((Usuario)session.getAttribute("usuarioLogeado")).getId());
        List<PartidaUsuarioPropiedad> propiedadesUsuarioActual = usuarioActual.getPropiedades();
        Partida partidaEnJuego = this.servicioMonopoly.obtenerPartidaPorPartidaId(partidaId);

        ObjectMapper jackson = new ObjectMapper();

        mp.put("partidaEnJuego",partidaEnJuego);
        mp.put("dado", session.getAttribute("dado"));
        mp.put("usuariosJugando", usuariosJugando);
        mp.put("usuariosJSON",jackson.writeValueAsString(usuariosJugando));
        mp.put("usuarioActual", usuarioActual);
        mp.put("usuarioPropiedadesActual", propiedadesUsuarioActual);

        /*Remuevo el atributo para que no aparezca 2 o más veces*/
        session.removeAttribute("dado");
        return new ModelAndView("monopoly.html",mp);
    }

    @RequestMapping("/moverJugador")
    public ModelAndView moverJugador(@RequestParam("id")Long idPartida,HttpSession session){
        PartidaUsuario  usuarioQuienTiro = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(idPartida, ((Usuario)session.getAttribute("usuarioLogeado")).getId());
        Partida partidaEnJuego = this.servicioMonopoly.obtenerPartidaPorPartidaId(idPartida);
        this.servicioMonopoly.moverJugadorAlCasillero(usuarioQuienTiro,session);
        this.servicioMonopoly.hacerCambioTurno(usuarioQuienTiro,partidaEnJuego);
        return new ModelAndView("redirect:/monopoly/?id="+partidaEnJuego.getId());
    }


    /*Adquirir una propiedad*/
    @RequestMapping("/adquirirPropiedad")
    public ModelAndView adquirirPropiedad(HttpServletRequest request){
        return null;
    }
}


