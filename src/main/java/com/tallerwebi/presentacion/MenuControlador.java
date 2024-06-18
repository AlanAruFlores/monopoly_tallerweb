package com.tallerwebi.presentacion;


import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.ServicioMenu;
import com.tallerwebi.dominio.ServicioMonopoly;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.infraestructura.ServicioMenuImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class MenuControlador {
    private ServicioMenu servicioMenu;

    @Autowired
    public MenuControlador(ServicioMenu servicioMenu){
        this.servicioMenu = servicioMenu;
    }

    @RequestMapping("/ir-menu")
    public ModelAndView irAlMenu(HttpSession session) {
        ModelMap mp = new ModelMap();
        PartidaUsuario partidaPendiente = this.servicioMenu.verSiTieneUnaPartidaEnCursoPorUsuario((Usuario) session.getAttribute("usuarioLogeado"));
        mp.put("partidaPendiente",partidaPendiente);
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