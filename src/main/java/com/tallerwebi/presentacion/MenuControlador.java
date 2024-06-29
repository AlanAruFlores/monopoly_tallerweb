package com.tallerwebi.presentacion;


import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class MenuControlador {
    @RequestMapping("/ir-menu")
    public ModelAndView irAlMenu() {
        return new ModelAndView("menu.html");
    }
    @RequestMapping("/ir-jugar")
    public ModelAndView irAJugar(){
        return new ModelAndView("redirect:/monopoly");
    }

    @RequestMapping("/ir-salir")
    public ModelAndView irASalir(){
        ModelMap model = new ModelMap();
        model.put("datosLogin", new DatosLogin2());
        return new ModelAndView("login", model);
    }
}