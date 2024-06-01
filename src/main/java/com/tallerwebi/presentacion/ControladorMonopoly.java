package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class ControladorMonopoly {

    ServicioMonopoly servicioMonopoly;

    @Autowired
    public ControladorMonopoly(ServicioMonopoly servicioMonopoly){
        this.servicioMonopoly = servicioMonopoly;
    }

    /*Ir al inicio del monopoly*/
    @RequestMapping("/monopoly")
    public ModelAndView irAlMonopoly(@RequestParam("id") Long partidaId, HttpSession session) {
        ModelMap mp  = new ModelMap();
        List<PartidaUsuario> usuariosJugando = this.servicioMonopoly.obtenerTodosLosUsuariosJugandoEnLaPartidaId(partidaId);
        PartidaUsuario usuarioActual = this.servicioMonopoly.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(partidaId, ((Usuario)session.getAttribute("usuarioLogeado")).getId());

        List<PartidaUsuarioPropiedad> propiedadesUsuarioActual = usuarioActual.getPropiedades();

        System.out.println(usuariosJugando);
        System.out.println(usuarioActual);
        System.out.println("PROPIEDADES:: "+propiedadesUsuarioActual);

        mp.put("usuariosJugando", usuariosJugando);
        mp.put("usuarioActual", usuarioActual);
        mp.put("usuarioPropiedadesActual", propiedadesUsuarioActual);
        return new ModelAndView("monopoly.html",mp);
    }

    /*Mover el jugador*/
    @RequestMapping("/moverJugador")
    public ModelAndView moverJugador(HttpServletRequest request){
        HttpSession session = request.getSession();
        this.servicioMonopoly.obtenerPosicionCasillero(session);
        ModelMap mp = new ModelMap();
        /*Establezco la posicion , imagen del dado y la aparicion de la ventana emergente*/
        mp.put("jugador", session.getAttribute("jugador"));
        mp.put("propiedad", session.getAttribute("propiedad"));
        mp.put("propiedades", session.getAttribute("propiedades"));

        mp.put("dado",session.getAttribute("dado"));
        return new ModelAndView("monopoly.html",mp);
    }

    /*Adquirir una propiedad*/
    @RequestMapping("/adquirirPropiedad")
    public ModelAndView adquirirPropiedad(HttpServletRequest request){
        return null;
    }
}


