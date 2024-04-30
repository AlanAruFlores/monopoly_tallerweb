package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MonopolyMenuControlador {

    @RequestMapping("/ir-menu")
    public ModelAndView irAlMenu() {
        return new ModelAndView("menu.html");
    }

    @RequestMapping("/ir-jugar")
    public ModelAndView irAJugar(){
        return new ModelAndView("jugar.html");
    }

    @RequestMapping("/ir-estadistica")
    public ModelAndView irAEstadistica(){
        return new ModelAndView("estadistica.html");
    }

    @RequestMapping("/ir-salir")
    public ModelAndView irASalir(){
        return new ModelAndView("login.html");
    }
}