package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private ServicioMonopoly servicioMonopoly;

    @Autowired
    public RestControladorPartida(ServicioPartida servicioPartida, ServicioMonopoly servicioMonopoly) {
        this.servicioPartida = servicioPartida;
        this.servicioMonopoly = servicioMonopoly;
    }

    @GetMapping("/obtenerTodasPartidas")
    public List<Partida> obtenerTodasLasPartidas(){
        return this.servicioPartida.obtenerTodasLasPartidas();
    }

    @RequestMapping(path="/obtenerPartidaDelCreador", method = RequestMethod.GET)
    public Partida obtenerPartidaPorCreador(HttpSession session){
        Usuario usuarioCreador = (Usuario) session.getAttribute("usuarioLogeado");
        Partida partidaRecienCreada = this.servicioPartida.obtenerPartidaPorCreador(usuarioCreador);
        return partidaRecienCreada;
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

    @RequestMapping(path="/eliminar", method = RequestMethod.GET)
    public void salirDeLaPartida(@RequestParam("partidaId") Long partidaId, @RequestParam("usuarioId") Long usuarioId){
        this.servicioPartida.salirDeLaPartida(partidaId, usuarioId);
    }


    @RequestMapping(path = "/establecerEstado", method = RequestMethod.POST)
    public void establecerEstadoDeUsuarioPartida(@RequestParam("idPartidaUsuario")Long idPartidaUsuario,@RequestParam("estado") String estado){
        System.out.println("SE EJECUTA SEGUNDO ESTABLECER ESTADO DEL "+estado);
        EstadoActividad estadoActividad = this.servicioMonopoly.convertirStringAEstadoActividad(estado);
        this.servicioMonopoly.establecerEstadoDeUnPartidaUsuario(idPartidaUsuario, estadoActividad);
    }
}
