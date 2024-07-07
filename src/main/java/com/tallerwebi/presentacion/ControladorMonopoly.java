package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioPerdedorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ControladorMonopoly {

    private ServicioMonopoly servicioMonopoly;
    private ServicioPartida servicioPartida;

    @Autowired
    public ControladorMonopoly(ServicioMonopoly servicioMonopoly, ServicioPartida servicioPartida){
        this.servicioMonopoly = servicioMonopoly;
        this.servicioPartida = servicioPartida;
    }

    /*Ir al inicio del monopoly*/
    @RequestMapping("/monopoly")
    public ModelAndView irAlMonopoly(@RequestParam("id") Long partidaId, HttpSession session) throws Exception {
        /*Cuando inicia el monopoly, primero cambio el estado de esa partida a activo*/
        //Partida partidaActualEnJuego = (Partida) session.getAttribute("partidaEnJuego");
        Partida partidaActualEnJuego = this.servicioPartida.obtenerPartidaPorPartidaId(partidaId);
        session.setAttribute("partidaEnJuego", partidaActualEnJuego);

        //Establezco el nuevo estado de la partida a la hora de ser inicializada
        if(partidaActualEnJuego != null && partidaActualEnJuego.getEstadoPartida().equals(EstadoPartida.ABIERTA))
            this.servicioPartida.actualizarEstadoDeUnaPartida(partidaActualEnJuego, EstadoPartida.EN_CURSO);

        ModelMap mp  = new ModelMap();
        PartidaUsuario usuarioActual = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(partidaId, ((Usuario)session.getAttribute("usuarioLogeado")).getId());
        List<PartidaUsuario> usuariosJugando = this.servicioMonopoly.obtenerTodosLosUsuariosJugandoEnLaPartidaId(partidaId);

        //Veo si el usuario se encuentra en la partida, en caso contrario va al menu
        System.out.println("USUARIO ACTUALLLLLLLLLLLLLLLLLLLLLLL:"+usuarioActual);
        if(usuarioActual == null)
            return new ModelAndView("redirect:/ir-menu");

        Boolean hayAlgunInactivo = this.servicioMonopoly.verificarSiAlgunoEstaInactivo(usuariosJugando);
        Boolean hayAlgunGanador = this.servicioMonopoly.verificarSiHayGanador(partidaId);
        //Obtengo todas las propiedades
        List<DatosPropiedadUsuario> datosDeLasPropiedadesDeLosUsuarios = this.servicioMonopoly.tenerDatosDeLasPropiedadesDeLosUsuarios(usuariosJugando);

        //Obtengo las propiedades de un unico usuario (usuario)
        List<PartidaUsuarioPropiedad> propiedadesUsuarioActual = usuarioActual.getPropiedades();

        Partida partidaEnJuego = this.servicioMonopoly.obtenerPartidaPorPartidaId(partidaId);

        ObjectMapper jackson = new ObjectMapper();



        /*A su vez evaluo si es algun emisor / receptor de un intercambio*/
        if(this.servicioMonopoly.buscarEmisorDeAlgunIntercambio(usuarioActual) != null) {
            Intercambio intercambio = this.servicioMonopoly.buscarEmisorDeAlgunIntercambio(usuarioActual);
            mp.put("esEmisor", true);
            mp.put("estadoIntercambio", intercambio.getEstado().name());
            mp.put("intercambio", intercambio);
        }
        //Si es receptor obtengo el intercambio y las propiedaddes a intercambiar
        if(this.servicioMonopoly.buscarReceptorDeAlgunIntercambio(usuarioActual) != null) {
            Intercambio intercambio = this.servicioMonopoly.buscarReceptorDeAlgunIntercambio(usuarioActual);
            List<IntercambioPropiedades> listaIntercambioPropiedades = this.servicioMonopoly.obtenerIntercambioPropiedadesPorIntercambio(intercambio);
            mp.put("esReceptor", true);
            mp.put("intercambio", intercambio);
            mp.put("intercambioPropiedades", listaIntercambioPropiedades);
            mp.put("estadoIntercambio", intercambio.getEstado().name());
        }

        mp.put("partidaEnJuego",partidaEnJuego);
        mp.put("propiedad",session.getAttribute("propiedad"));
        mp.put("dado", session.getAttribute("dado"));
        mp.put("mensaje", session.getAttribute("mensaje"));
        mp.put("pagarMensaje", session.getAttribute("pagarMensaje"));
        mp.put("usuariosJugando", usuariosJugando);
        mp.put("usuariosJSON",jackson.writeValueAsString(usuariosJugando));
        mp.put("usuarioActual", usuarioActual);
        mp.put("usuarioPropiedadesActual", propiedadesUsuarioActual);
        mp.put("bancarrota",session.getAttribute("bancarrota"));
        mp.put("datosPropiedadesUsuariosJSON",jackson.writeValueAsString(datosDeLasPropiedadesDeLosUsuarios));
        //Me va a servir para ver si hay algun inactivo en el juego, y asi poder eliminarlo
        mp.put("hayGanador",hayAlgunGanador);
        mp.put("hayAlgunInactivo",hayAlgunInactivo);
        //System.out.println(hayAlgunInactivo);

        mp.put("deshipotecarError", session.getAttribute("deshipotecarError"));

        /*Remuevo el atributo para que no aparezca 2 o más veces*/
        session.removeAttribute("dado");
        session.removeAttribute("propiedad");
        session.removeAttribute("mensaje");
        session.removeAttribute("pagarMensaje");
        session.removeAttribute("bancarrota");
        session.removeAttribute("hayGanador");
        session.removeAttribute("deshipotecarError");

        //System.out.println(jackson.writeValueAsString(usuariosJugando));
        //System.out.println(usuariosJugando.size());
        return new ModelAndView("monopoly.html",mp);
    }
    @RequestMapping("/aceptarDado")
    public void aceptarDado(HttpSession session){
        session.removeAttribute("dado");
    }

    @RequestMapping("/moverJugador")
    public ModelAndView moverJugador(@RequestParam("id")Long idPartida, HttpSession session){
        PartidaUsuario  usuarioQuienTiro = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(idPartida, ((Usuario)session.getAttribute("usuarioLogeado")).getId());
        Partida partidaEnJuego = this.servicioMonopoly.obtenerPartidaPorPartidaId(idPartida);
       try{
           this.servicioMonopoly.hacerCambioTurno(usuarioQuienTiro,partidaEnJuego);
           this.servicioMonopoly.moverJugadorAlCasillero(usuarioQuienTiro,session);
       }catch(UsuarioPerdedorException ex){
           session.setAttribute("bancarrota", "El jugador "+usuarioQuienTiro.getUsuario().getNombreUsuario()+ " quedo en bancarrota");
       }
       return new ModelAndView("redirect:/monopoly/?id="+partidaEnJuego.getId());
    }


    /*Adquirir una propiedad*/
    @RequestMapping("/adquirirPropiedad")
    public ModelAndView adquirirPropiedad(@RequestParam("idPartida") Long idPartida, @RequestParam("idPropiedad") Long idPropiedad, HttpSession session){
        PartidaUsuario  usuarioQuienQuiereComprar = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(idPartida, ((Usuario)session.getAttribute("usuarioLogeado")).getId());
        try {
            this.servicioMonopoly.adquirirPropiedad(idPropiedad, usuarioQuienQuiereComprar);
            session.setAttribute("mensaje", "Propiedad Comprada");
        } catch (SaldoInsuficienteException e) {
            session.setAttribute("mensaje","No posee saldo suficiente");
        }
        return new ModelAndView("redirect:/monopoly/?id="+idPartida);
    }


    @RequestMapping("/salirPartidaGanador")
    public ModelAndView salirPartidaComoGanador(@RequestParam("idPartida") Long idPartida, @RequestParam("idPartidaUsuario") Long idPartidaUsuario){
        PartidaUsuario ganador = this.servicioMonopoly.obtenerPartidaUsuarioPorId(idPartidaUsuario);
        this.servicioMonopoly.actualizarEstadisticasDelUsuarioEnLaPartida(ganador);
        this.servicioPartida.salirDeLaPartida(idPartida, ganador.getUsuario().getId());
        this.servicioPartida.eliminarPartida(idPartida);
        return new ModelAndView("redirect:/ir-menu");
    }


    @RequestMapping("/hipotecar")
    public ModelAndView hipotecarPropiedad(@RequestParam("idPartida") Long partidaId, @RequestParam("idPropiedadUsuario") Integer idPropiedadUsuario){
        this.servicioMonopoly.hipotecarPropiedad(idPropiedadUsuario);
        return new ModelAndView("redirect:/monopoly?id="+partidaId);
    }

    @RequestMapping("/deshipotecar")
    public ModelAndView deshipotecarPropiedad(@RequestParam("idPartida") Long partidaId, @RequestParam("idPropiedadUsuario") Integer idPropiedadUsuario, HttpSession session){
        try {
            this.servicioMonopoly.deshipotecarPropiedad(idPropiedadUsuario);
        } catch (SaldoInsuficienteException e) {
            session.setAttribute("deshipotecarError", true);
        }

        return new ModelAndView("redirect:/monopoly?id="+partidaId);
    }
}


