package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.ServicioMonopoly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/intercambio")
public class RestControllerIntercambio {

    private ServicioMonopoly servicioMonopoly;

    @Autowired
    public RestControllerIntercambio(ServicioMonopoly servicioMonopoly){
        this.servicioMonopoly = servicioMonopoly;
    }

    @PostMapping("/hacerIntercambio")
    public void hacerIntercambio(@RequestBody DatosIntercambio datosIntercambio){
        this.servicioMonopoly.hacerIntercambio(datosIntercambio);
    }
}
