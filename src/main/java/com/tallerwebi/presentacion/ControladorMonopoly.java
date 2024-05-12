package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMonopoly;
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
        //Establezco como valor inicial la casilla 1
        session.setAttribute("numeroRandom",1);
        ModelMap mp  = new ModelMap();
        mp.put("posicion", session.getAttribute("numeroRandom"));
        return new ModelAndView("monopoly.html",mp);
    }

    @RequestMapping("/moverJugador")
    public ModelAndView moverJugador(HttpServletRequest request){
        HttpSession session = request.getSession();
        this.servicioMonopoly.obtenerPosicionCasillero(session);
        ModelMap mp = new ModelMap();
        /*Establezco la posicion , imagen del dado y la aparicion de la ventana emergente*/
        mp.put("posicion", session.getAttribute("numeroRandom"));
        mp.put("dado",session.getAttribute("dado"));
        mp.put("propiedad", session.getAttribute("propiedad"));

        return new ModelAndView("monopoly.html",mp);
    }
}


