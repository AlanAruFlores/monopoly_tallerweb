package com.tallerwebi.presentacion;
import com.tallerwebi.dominio.EstadoIntercambio;
import com.tallerwebi.dominio.Intercambio;
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


    @RequestMapping(path="/cambiarEstadoAceptado", method = RequestMethod.GET)
    public void cambiarEstadoAceptado(@RequestParam("id") Long idIntercambio){
        Intercambio intercambio  = this.servicioMonopoly.buscarIntercambioPorId(idIntercambio);
        intercambio.setEstado(EstadoIntercambio.ACEPTADO);
        this.servicioMonopoly.actualizarIntercambio(intercambio);
    }

    @RequestMapping(path="/cambiarEstadoRechazado", method = RequestMethod.GET)
    public void cambiarEstadoRechazado(@RequestParam("id") Long idIntercambio){
        Intercambio intercambio  = this.servicioMonopoly.buscarIntercambioPorId(idIntercambio);
        intercambio.setEstado(EstadoIntercambio.RECHAZADO);
        this.servicioMonopoly.actualizarIntercambio(intercambio);
    }
}
