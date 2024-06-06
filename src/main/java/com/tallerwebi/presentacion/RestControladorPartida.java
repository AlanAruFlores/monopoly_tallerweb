package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.EstadoPartida;
import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;


@RestController
@RequestMapping("/api/partida")
public class RestControladorPartida {
    private ServicioPartida servicioPartida;

    @Autowired
    public RestControladorPartida(ServicioPartida servicioPartida) {
        this.servicioPartida = servicioPartida;
    }

    @GetMapping("/obtenerTodasPartidas")
    public List<Partida> obtenerTodasLasPartidas(){
        return this.servicioPartida.obtenerTodasLasPartidas();
    }


    /*Creo una partida*/
    @PostMapping("/crearPartida")
    public void crearPartida(@RequestBody DatosPartida datosDepartida, HttpSession session){
        Partida partidaNueva = new Partida();
        partidaNueva.setNombre(datosDepartida.nombre);
        partidaNueva.setNumeroJugadores(Integer.parseInt(datosDepartida.numero));
        partidaNueva.setCreador((Usuario) session.getAttribute("usuarioLogeado"));
        partidaNueva.setFechaApertura(LocalDate.now());
        partidaNueva.setEstadoPartida(EstadoPartida.ABIERTA);

        /*El primer turno va a ser del jugador quien creo la partida*/
        partidaNueva.setTurnoJugador((Usuario) session.getAttribute("usuarioLogeado"));
        this.servicioPartida.crearUnaPartidaNueva(partidaNueva);
    }


}
