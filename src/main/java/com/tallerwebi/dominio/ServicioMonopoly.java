package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

public interface ServicioMonopoly {
    public void registrarJugador(Jugador jugador);
    public Jugador obtenerJugadorPorUsuarioId(Long usuarioId);
    public void obtenerPosicionCasillero(HttpSession session);
    public void adquirirPropiedadPorElJugador(Jugador jugador,Propiedad propiedad) throws SaldoInsuficienteException;
}
