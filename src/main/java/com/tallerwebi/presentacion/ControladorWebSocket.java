package com.tallerwebi.presentacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tallerwebi.dominio.MensajeEnviado;
import com.tallerwebi.dominio.MensajeRecibido;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class ControladorWebSocket {
    //WEB SOCKET METODOS

    /*Web socket para la partida*/
    @MessageMapping("/partidaNueva")
    @SendTo("/topic/notificacionPartida")
    public String notificarCreacionDeNuevaPartida(MensajeRecibido mensaje) throws Exception {
        MensajeEnviado mensajeEnviado = new MensajeEnviado(mensaje.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensajeEnviado);
        return json;
    }

    /*Web socket para la sala de espera*/
    @MessageMapping("/enviarNotificacionSalaEspera")
    @SendTo("/topic/recibirNotificacionSalaEspera")
    public String notificarUsuarioNuevoEnLaPartida(MensajeRecibido mensaje) throws Exception {
        MensajeEnviado mensajeEnviado = new MensajeEnviado(mensaje.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensajeEnviado);
        return json;
    }

    @MessageMapping("/enviarEmpezoJuego")
    @SendTo("/topic/recibirEmpezoJuego")
    public String notificarEmpezarElJuego(MensajeRecibido mensaje) throws Exception {
        MensajeEnviado mensajeEnviado = new MensajeEnviado(mensaje.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensajeEnviado);
        return json;
    }

    /*Web socket para el chat en el juego del monopoly*/

    //Para actualizar los tableros
    @MessageMapping("/enviarActualizacionDeTablero")
    @SendTo("/topic/recibirActualizacionDeTablero")
    public String recibirActualizacionDeTablero(MensajeEnviado mensaje) throws Exception {
        MensajeEnviado mensajeEnviado = new MensajeEnviado(mensaje.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensajeEnviado);
        return json;
    }

    //Para el chat
    @MessageMapping("/enviarMensajeChat")
    @SendTo("/topic/recibirMensajeChat")
    public String recibirEnviarMensajeEnElChat(MensajeEnviado mensaje) throws Exception {
        MensajeEnviado mensajeEnviado = new MensajeEnviado(mensaje.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensajeEnviado);
        return json;
    }

    @MessageMapping("/indicarCierreSocket")
    @SendTo("/topic/recibirCierreSocket")
    public String recibirCierreDeUnJugaodr(MensajeEnviado mensaje) throws Exception{
        MensajeEnviado mensajeEnviado = new MensajeEnviado(mensaje.getMessage());
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(mensajeEnviado);
        return json;
    }
}
