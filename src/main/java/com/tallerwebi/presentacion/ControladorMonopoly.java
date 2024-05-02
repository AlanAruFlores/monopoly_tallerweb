package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControladorMonopoly {

    @RequestMapping("/monopoly")
    public String showMonopolyPage() {
        return "monopoly";
    }
}
