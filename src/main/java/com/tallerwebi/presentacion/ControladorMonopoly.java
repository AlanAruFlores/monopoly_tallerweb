package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorMonopoly {

    @RequestMapping("/monopoly")
    public String showMonopolyPage() {
        return "monopoly";
    }

    @RequestMapping("/ir-menu")
    public ModelAndView irAlMenu() {
        return new ModelAndView("menu.html");
    }


}


