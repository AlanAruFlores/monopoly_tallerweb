package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Jugador;
import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.ServicioMonopoly;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorMonopoly {

    ServicioMonopoly servicioMonopoly;

    @Autowired
    public ControladorMonopoly(ServicioMonopoly servicioMonopoly){
        this.servicioMonopoly = servicioMonopoly;
    }

    @RequestMapping("/monopoly")
    public ModelAndView showMonopolyPage(HttpServletRequest request) {
        //Obtengo la sesion
        HttpSession session = request.getSession();
        //Obtener Jugador
        Jugador jugador = servicioMonopoly.obtenerJugadorPorUsuarioId(1L);
        //Establezco como valor inicial la casilla
        session.setAttribute("jugador",jugador);
        ModelMap mp  = new ModelMap();
        mp.put("jugador", session.getAttribute("jugador"));
        return new ModelAndView("monopoly.html",mp);
    }

    @RequestMapping("/moverJugador")
    public ModelAndView moverJugador(HttpServletRequest request){
        HttpSession session = request.getSession();
        this.servicioMonopoly.obtenerPosicionCasillero(session);
        ModelMap mp = new ModelMap();
        /*Establezco la posicion , imagen del dado y la aparicion de la ventana emergente*/
        mp.put("jugador", session.getAttribute("jugador"));
        mp.put("propiedad", session.getAttribute("propiedad"));

        mp.put("dado",session.getAttribute("dado"));
        return new ModelAndView("monopoly.html",mp);
    }

    @RequestMapping("/adquirirPropiedad")
    public ModelAndView adquirirPropiedad(HttpServletRequest request){
        HttpSession session = request.getSession();
        Jugador jugador = (Jugador)session.getAttribute("jugador");
        Propiedad propiedad = (Propiedad)session.getAttribute("propiedad");

        ModelMap mp  = new ModelMap();
        mp.put("jugador", session.getAttribute("jugador"));

        try {
            this.servicioMonopoly.adquirirPropiedadPorElJugador(jugador, propiedad);
            mp.put("mensaje",propiedad.getNombre()+ " comprada");
        }catch(SaldoInsuficienteException ex){
            mp.put("mensaje", jugador.getUsuario().getNombreUsuario()+" no posee saldo suficiente");
        }

        return new ModelAndView("monopoly.html",mp);
    }
}


