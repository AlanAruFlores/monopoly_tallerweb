package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("servicioMonopoly")
@Transactional
public class ServiceMonopolyImpl implements ServicioMonopoly {

    private RepositorioPropiedad repositorioPropiedad;
    private RepositorioJugador repositorioJugador;

    /*Enlace de Dados y Su numero*/
    Map<Integer, String> mapaDado = new HashMap<Integer, String>();

    @Autowired
    public ServiceMonopolyImpl(RepositorioPropiedad repositorioPropiedad, RepositorioJugador repositorioJugador) {
        this.repositorioPropiedad = repositorioPropiedad;
        this.repositorioJugador = repositorioJugador;

        /*Llenamos datos al mapa */
        mapaDado.put(1,"/imagenes/dados/dado1.png");
        mapaDado.put(2,"/imagenes/dados/dado2.png");
        mapaDado.put(3,"/imagenes/dados/dado3.png");
        mapaDado.put(4,"/imagenes/dados/dado4.png");
        mapaDado.put(5,"/imagenes/dados/dado5.png");
        mapaDado.put(6,"/imagenes/dados/dado6.png");
    }


    @Override
    public void registrarJugador(Jugador jugador) {
      this.repositorioJugador.guardar(jugador);
    }

    @Override
    public Jugador obtenerJugadorPorUsuarioId(Long usuarioId){
        return this.repositorioJugador.obtenerJugador(usuarioId);
    }

    @Override
    public void obtenerPosicionCasillero(HttpSession session) {
        Integer numeroRandom = (int) (1 + (Math.random() * (6 - 1)));
        //Obtengo el jugador
        Jugador jugador = (Jugador) session.getAttribute("jugador");

        session.setAttribute("dado",mapaDado.get(numeroRandom));
        /*Actualizo posicion*/
        Integer posicionJugador;
        posicionJugador = jugador.getPosicionCasilla();
        numeroRandom += posicionJugador;
        if(numeroRandom > 20){
            numeroRandom = (numeroRandom-20) ;
        }
        jugador.setPosicionCasilla(numeroRandom);
        //Actualizo la posicion
        this.repositorioJugador.actualizar(jugador);
        session.setAttribute("jugador", jugador);

        //Agrego propiedad
        Propiedad propiedadEncontrada = repositorioPropiedad.obtenerPropiedadPorNroCasillero(numeroRandom);
        if(propiedadEncontrada != null && propiedadEncontrada.getDisponibilidad())
            session.setAttribute("propiedad", propiedadEncontrada);
        else
            session.setAttribute("propiedad",null);
    }

    /*Metodo para adquirir la propiedad/servicio al jugador*/
    @Override
    public void adquirirPropiedadPorElJugador(Jugador jugador,Propiedad propiedad) throws SaldoInsuficienteException{
        if(propiedad.getPrecio() > jugador.getSaldo())
            throw new SaldoInsuficienteException();

        jugador.setSaldo(jugador.getSaldo()-propiedad.getPrecio());

        propiedad.setDisponibilidad(false);
        propiedad.setPropietario(jugador);

        this.repositorioJugador.actualizar(jugador);
        this.repositorioPropiedad.actualizar(propiedad);
    }

    @Override
    public List<Propiedad> obtenerPropiedadesPorJugadorId(Long jugadorId) {
        return this.repositorioPropiedad.obtenerPropiedadesPorJugadorId(jugadorId);
    }
}
