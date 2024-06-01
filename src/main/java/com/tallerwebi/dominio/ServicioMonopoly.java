package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.List;

public interface ServicioMonopoly {
    public void obtenerPosicionCasillero(HttpSession session);
    public List<PartidaUsuario> obtenerTodosLosUsuariosJugandoEnLaPartidaId(Long partidaId);
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId);
}
