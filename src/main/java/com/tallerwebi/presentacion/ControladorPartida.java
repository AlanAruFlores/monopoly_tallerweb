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
    public ModelAndView irPartida(HttpSession session){
        ModelMap mp = new ModelMap();
        //Obtengo las partidas para mostrarlos a los jugadores
        List<Partida> partidasCreadas = this.servicioPartida.obtenerTodasLasPartidas();

        mp.put("partida", new Partida());
        mp.put("partidasCreadas",partidasCreadas);

        return new ModelAndView("partidas.html", mp);
    }

    /*Creo una partida*/
    @RequestMapping(value = "/crearPartida", method = RequestMethod.POST)
    public ModelAndView crearPartida(@ModelAttribute("partida") Partida partida, HttpSession session){
        partida.setCreador((Usuario) session.getAttribute("usuarioLogeado"));
        partida.setFechaApertura(LocalDate.now());
        partida.setEstadoPartida(EstadoPartida.ABIERTA);

        this.servicioPartida.crearUnaPartidaNueva(partida);
        return new ModelAndView("redirect:/partida",new ModelMap());
    }


    @RequestMapping("/unirsePartida")
    public ModelAndView unirseAUnaPartida(@RequestParam("id") Long partidaId, HttpSession session) throws ExcesoDeJugadoresException {
        try{
            servicioPartida.unirseAPartida(partidaId,(Usuario)session.getAttribute("usuarioLogeado"));
        }catch(ExcesoDeJugadoresException ex){
            ModelMap mp = new ModelMap();
            mp.put("mensaje", "No puede entrar a la partida, ya esta lleno");
            return new ModelAndView("redirect:/partida",mp);
        }

        return new ModelAndView("redirect:/espera/?id="+partidaId);
    }

    @RequestMapping("/espera")
    public ModelAndView irSalaEspera(@RequestParam("id") Long partidaId, HttpSession session){
        ModelMap mp = new ModelMap();
        List<Usuario> usuarioEnLaSalaEspera = this.servicioPartida.verUsuariosEnlaPartidaEspera(partidaId);
        mp.put("usuariosConectados",usuarioEnLaSalaEspera);
        mp.put("partidaIdActual",partidaId);

        Usuario creadorUsuario  = this.servicioPartida.obtenerCreadoUsuarioDeUnaPartida(partidaId);
        mp.put("creadorUsuario", creadorUsuario);
        mp.put("usuarioActual", session.getAttribute("usuarioLogeado"));

        System.out.println(creadorUsuario);
        System.out.println((Usuario)session.getAttribute("usuarioLogeado"));
        return new ModelAndView("sala_espera.html", mp);
    }

    @RequestMapping("/salirPartida")
    public ModelAndView salirPartidaUsuario(@RequestParam("id") Long partidaId, HttpSession session){
        /*Si quiere salir, lo saco de la sala de espera*/
        this.servicioPartida.salirDeLaPartida(partidaId,((Usuario)session.getAttribute("usuarioLogeado")).getId());
        return new ModelAndView("redirect:/ir-menu");
    }

}
