package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoPartida;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
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
        ModelMap mp = new ModelMap();
        partida.setCreador((Usuario) session.getAttribute("usuarioLogeado"));
        partida.setFechaApertura(LocalDate.now());
        partida.setEstadoPartida(EstadoPartida.ABIERTA);

        this.servicioPartida.crearUnaPartidaNueva(partida);
        mp.put("partida", partida);
        return new ModelAndView("redirect:/espera",mp);
    }

    @RequestMapping("/espera")
    public ModelAndView irSalaEspera(){
        return new ModelAndView("sala_espera.html", new ModelMap());
    }
}
