package com.tallerwebi.presentacion;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioPerdedorException;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
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
        Partida partidaActualEnJuego = (Partida) session.getAttribute("partidaEnJuego");
        if(partidaActualEnJuego.getEstadoPartida().equals(EstadoPartida.ABIERTA))
            this.servicioPartida.actualizarEstadoDeUnaPartida(partidaActualEnJuego, EstadoPartida.EN_CURSO);

        ModelMap mp  = new ModelMap();
        PartidaUsuario usuarioActual = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(partidaId, ((Usuario)session.getAttribute("usuarioLogeado")).getId());
        List<PartidaUsuario> usuariosJugando = this.servicioMonopoly.obtenerTodosLosUsuariosJugandoEnLaPartidaId(partidaId);

        Boolean hayAlgunInactivo = this.servicioMonopoly.verificarSiAlgunoEstaInactivo(usuariosJugando);
        //Obtengo todas las propiedades
        List<DatosPropiedadUsuario> datosDeLasPropiedadesDeLosUsuarios = this.servicioMonopoly.tenerDatosDeLasPropiedadesDeLosUsuarios(usuariosJugando);

        //Obtengo las propiedades de un unico usuario (usuario)
        List<PartidaUsuarioPropiedad> propiedadesUsuarioActual = usuarioActual.getPropiedades();

        Partida partidaEnJuego = this.servicioMonopoly.obtenerPartidaPorPartidaId(partidaId);

        ObjectMapper jackson = new ObjectMapper();

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
        mp.put("hayAlgunInactivo",hayAlgunInactivo);
        System.out.println(hayAlgunInactivo);

        /*Remuevo el atributo para que no aparezca 2 o más veces*/
        session.removeAttribute("dado");
        session.removeAttribute("propiedad");
        session.removeAttribute("mensaje");
        session.removeAttribute("pagarMensaje");
        session.removeAttribute("bancarrota");

        System.out.println(jackson.writeValueAsString(usuariosJugando));
        System.out.println(usuariosJugando.size());
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


}


