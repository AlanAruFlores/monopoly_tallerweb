package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcesoDeJugadoresException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpSession;

@Controller
public class ControladorPartida {
    private ServicioPartida servicioPartida;
    @Autowired
    public ControladorPartida(ServicioPartida servicioPartida) {
        this.servicioPartida = servicioPartida;
    }

    /*Voy a la pantalla de partidas*/
    @RequestMapping("/partida")
    public ModelAndView irPartida(){
        ModelMap mp = new ModelMap();
        //Obtengo las partidas para mostrarlos a los jugadores
        List<Partida> partidasCreadas = this.servicioPartida.obtenerTodasLasPartidas();

        mp.put("partida", new Partida());
        mp.put("partidasCreadas",partidasCreadas);
        return new ModelAndView("partidas.html", mp);
    }

    /*Creo una partida
    @RequestMapping(value = "/crearPartida", method = RequestMethod.POST)
    public ModelAndView crearPartida(@ModelAttribute("partida") Partida partida, HttpSession session){
        partida.setCreador((Usuario) session.getAttribute("usuarioLogeado"));
        partida.setFechaApertura(LocalDate.now());
        partida.setEstadoPartida(EstadoPartida.ABIERTA);

        //El primer turno va a ser del jugador quien creo la partida
        partida.setTurnoJugador((Usuario) session.getAttribute("usuarioLogeado"));

        this.servicioPartida.crearUnaPartidaNueva(partida);
        return new ModelAndView("redirect:/partida",new ModelMap());
    }
    */


    @RequestMapping("/unirsePartida")
    public ModelAndView unirseAUnaPartida(@RequestParam("id") Long partidaId, HttpSession session) throws ExcesoDeJugadoresException {
        try{
            Partida partidaAUnirse = this.servicioPartida.obtenerPartidaPorPartidaId(partidaId);
            //Agrego a que se una a la partida
            servicioPartida.unirseAPartida(partidaAUnirse,(Usuario)session.getAttribute("usuarioLogeado"));
            //Establezco en la session la partida en la que se unio
            session.setAttribute("partidaEnJuego", partidaAUnirse);

        }catch(ExcesoDeJugadoresException ex){
            ModelMap mp = new ModelMap();
            mp.put("mensaje", "No puede entrar a la partida, ya esta lleno");
            return new ModelAndView("redirect:/partida",mp);
        }

        return new ModelAndView("redirect:/espera");
    }

    @RequestMapping("/espera")
    public ModelAndView irSalaEspera(HttpSession session){
        ModelMap mp = new ModelMap();
        Partida partidaEnJuego = (Partida) session.getAttribute("partidaEnJuego");

        List<Usuario> usuarioEnLaSalaEspera = this.servicioPartida.verUsuariosEnlaPartidaEspera(partidaEnJuego.getId());
        mp.put("usuariosConectados",usuarioEnLaSalaEspera);
        mp.put("partidaActual",partidaEnJuego);

        Usuario creadorUsuario  = this.servicioPartida.obtenerCreadoUsuarioDeUnaPartida(partidaEnJuego.getId());
        mp.put("creadorUsuario", creadorUsuario);
        mp.put("usuarioActual", session.getAttribute("usuarioLogeado"));

        return new ModelAndView("sala_espera.html", mp);
    }

    /*
    @RequestMapping("/empezarPartida")
    public ModelAndView empezarLaPartida(HttpSession session){
        Partida partidaEnJuego = (Partida) session.getAttribute("partidaEnJuego");
        partidaEnJuego = this.servicioPartida.actualizarEstadoDeLaPartida(partidaEnJuego, EstadoPartida.EN_CURSO);
    }*/


    @RequestMapping("/salirPartida")
    public ModelAndView salirPartidaUsuario(@RequestParam("id") Long partidaId, HttpSession session){
        /*Si quiere salir, lo saco de la sala de espera*/
        this.servicioPartida.salirDeLaPartida(partidaId,((Usuario)session.getAttribute("usuarioLogeado")).getId());
        return new ModelAndView("redirect:/ir-menu");
    }

}
