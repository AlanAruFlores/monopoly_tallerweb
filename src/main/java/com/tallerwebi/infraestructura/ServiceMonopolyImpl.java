package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("servicioMonopoly")
@Transactional
public class ServiceMonopolyImpl implements ServicioMonopoly{
    private RepositorioPartidaUsuario repositorioPartidaUsuario;
    private RepositorioPartida repositorioPartida;

    /*Enlace de Dados y Su numero*/
    Map<Integer, String> mapaDado = new HashMap<Integer, String>();
    @Autowired
    public ServiceMonopolyImpl(RepositorioPartidaUsuario repositorioPartidaUsuario, RepositorioPartida repositorioPartida) {
        this.repositorioPartidaUsuario = repositorioPartidaUsuario;
        this.repositorioPartida = repositorioPartida;

        /*Llenamos datos al mapa */
        mapaDado.put(1,"/imagenes/dados/dado1.png");
        mapaDado.put(2,"/imagenes/dados/dado2.png");
        mapaDado.put(3,"/imagenes/dados/dado3.png");
        mapaDado.put(4,"/imagenes/dados/dado4.png");
        mapaDado.put(5,"/imagenes/dados/dado5.png");
        mapaDado.put(6,"/imagenes/dados/dado6.png");
    }


    private Integer obtenerPosicionCasillero(Integer posicionUsuario,HttpSession session) {
        Integer posicionObtenida = (int) (1 + (Math.random() * (6 - 1)));
        session.setAttribute("dado",mapaDado.get(posicionObtenida));
        posicionObtenida += posicionUsuario;
        if(posicionObtenida > 20)
            posicionObtenida = (posicionObtenida-20) ;

        return posicionObtenida;
    }

    @Override
    public void moverJugadorAlCasillero(PartidaUsuario usuarioAMover,HttpSession session){
        Integer posicionObtenida = obtenerPosicionCasillero(usuarioAMover.getPosicionCasilla(),session);
        //Determino si piso en alguna propiedad

        //Establezco su posicion
        usuarioAMover.setPosicionCasilla(posicionObtenida);
        this.repositorioPartidaUsuario.actualizarPartidaUsuario(usuarioAMover);
    }

    private Propiedad determinarSiPisoEnAlgunaPropiedadDisponible(Integer posicionDelUsuario){
        
        return null;
    }

    @Override
    public void hacerCambioTurno(PartidaUsuario partidaUsuario, Partida partidaEnJuego) {
        /*obtengo todos los usuarios jugando a la misma partida*/
        List<PartidaUsuario> usuarioJugandoALaMismaPartida = this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(partidaEnJuego.getId());
        System.out.println("JUGADORES::: "+usuarioJugandoALaMismaPartida);

        /*Hago el cambio de turno*/
        int ordenTurnoActual = usuarioJugandoALaMismaPartida.indexOf(partidaUsuario);
        System.out.println(ordenTurnoActual);
        int siguienteTurno = (ordenTurnoActual + 1) % usuarioJugandoALaMismaPartida.size();

        PartidaUsuario proximoUsuarioATirar = usuarioJugandoALaMismaPartida.get(siguienteTurno);
        partidaEnJuego.setTurnoJugador(proximoUsuarioATirar.getUsuario());

        /*Guardo cambios*/
        this.repositorioPartida.actualizarPartida(partidaEnJuego);
    }


    @Override
    public List<PartidaUsuario> obtenerTodosLosUsuariosJugandoEnLaPartidaId(Long partidaId) {
        return this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(partidaId);
    }

    @Override
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId) {
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(partidaId,usuarioId);
    }

    @Override
    public Partida obtenerPartidaPorPartidaId(Long partidaId) {
        return this.repositorioPartida.obtenerPartidaPorId(partidaId);
    }


}
