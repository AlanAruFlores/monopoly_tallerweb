package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorPartida {

    @RequestMapping("/partida")
    public ModelAndView irPartida(){
        return new ModelAndView("partidas.html", new ModelMap());
    }

    @RequestMapping("/espera")
    public ModelAndView irSalaEspera(){
        return new ModelAndView("sala_espera.html", new ModelMap());
    }
}
