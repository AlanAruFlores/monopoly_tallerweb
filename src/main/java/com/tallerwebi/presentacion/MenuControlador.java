package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MenuControlador {
    @RequestMapping("/ir-menu")
    public ModelAndView irAlMenu(HttpSession session) {
        ModelMap mp = new ModelMap();

        mp.put("partidaEnJuego",session.getAttribute("partidaEnJuego"));
        return new ModelAndView("menu.html",mp);
    }
    @RequestMapping("/ir-jugar")
    public ModelAndView irAJugar(){
        return new ModelAndView("redirect:/monopoly");
    }
    @RequestMapping("/ir-salir")
    public ModelAndView irASalir(HttpSession session){
        session.removeAttribute("usuarioLogeado");
        return new ModelAndView("redirect:/login");
    }
}