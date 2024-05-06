package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioTablero;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class ControladorTablero {
    ServicioTablero servicioTablero;

    @Autowired
    public ControladorTablero(ServicioTablero servicioTablero){
        this.servicioTablero = servicioTablero;
    }

    @RequestMapping("/tablero")
    public ModelAndView irTablero(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("numeroRandom",1);
        ModelMap mp  = new ModelMap();
        mp.put("posicion", session.getAttribute("numeroRandom"));
        return new ModelAndView("tablero.html",mp);
    }


    @RequestMapping("/obtenerNumeroRandom")
    public ModelAndView obtenerNumeroRandom(HttpServletRequest request){
        HttpSession session = request.getSession();
        servicioTablero.obtenerRandom(session);

        ModelMap mp = new ModelMap();
        mp.put("posicion", session.getAttribute("numeroRandom"));
        return new ModelAndView("tablero.html",mp);
    }
}
