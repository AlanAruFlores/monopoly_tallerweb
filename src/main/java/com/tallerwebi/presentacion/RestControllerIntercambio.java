package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.ServicioMonopoly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api/intercambio")
public class RestControllerIntercambio {

    private ServicioMonopoly servicioMonopoly;

    @Autowired
    public RestControllerIntercambio(ServicioMonopoly servicioMonopoly){
        this.servicioMonopoly = servicioMonopoly;
    }

    @RequestMapping("/hacerIntercambio")
    public ModelAndView hacerIntercambio(@ModelAttribute("datosIntercambio") DatosIntercambio datosIntercambio){

        return null;
    }
}
