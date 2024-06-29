package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioPerfil;
import com.tallerwebi.dominio.ServicioRanking;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class ControladorRanking {

    private ServicioRanking servicioRanking;

    @Autowired
    public ControladorRanking(ServicioRanking servicioRanking) {
        this.servicioRanking = servicioRanking;
    }

    public ControladorRanking() {}

    @RequestMapping("/ranking")
    public ModelAndView ranking(Model model) {
        List<Usuario> usuarios = servicioRanking.findAllSortedByVictorias();
        model.addAttribute("usuarios", usuarios);
        return new ModelAndView("ranking.html");
    }

    @RequestMapping("/volver-menu")
    public ModelAndView irAMenu() {
        return new ModelAndView("menu.html");
    }
}
