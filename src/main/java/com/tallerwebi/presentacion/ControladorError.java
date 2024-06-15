package com.tallerwebi.presentacion;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ControladorError {

    @RequestMapping(path = "/error", method = RequestMethod.GET)
    public ModelAndView irAlErrorAuthentication(@RequestParam("codigo") Integer codigoError, @RequestParam("mensaje") String mensaje){
        ModelMap modelMap = new ModelMap();
        modelMap.addAttribute("codigoError", codigoError);
        modelMap.addAttribute("mensaje", mensaje);
        return new ModelAndView("error", modelMap);
    }
}
