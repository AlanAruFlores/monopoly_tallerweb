package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioPerdedorException;
import com.tallerwebi.presentacion.DatosIntercambio;
import com.tallerwebi.presentacion.DatosPropiedadUsuario;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ServicioMonopoly {
    public void moverJugadorAlCasillero(PartidaUsuario usuarioAMover,HttpSession session) throws UsuarioPerdedorException;
    public void hacerCambioTurno(PartidaUsuario partidaUsuario,Partida partidaEnJuego);
    public List<PartidaUsuario> obtenerTodosLosUsuariosJugandoEnLaPartidaId(Long partidaId);
    public List<DatosPropiedadUsuario> tenerDatosDeLasPropiedadesDeLosUsuarios(List<PartidaUsuario> usuariosJugando);
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId);
    public Partida obtenerPartidaPorPartidaId(Long partidaId);
    public void adquirirPropiedad(Long propiedadId, PartidaUsuario usuarioQuienCompra) throws SaldoInsuficienteException;
    public void establecerEstadoDeUnPartidaUsuario(Long partidaUsuarioId, EstadoActividad estado);
    public EstadoActividad convertirStringAEstadoActividad(String estado);
    public void establecerActivo(PartidaUsuario partidaUsuario);
    public Boolean verificarSiAlgunoEstaInactivo(List<PartidaUsuario> partidasUsuarios);

    public void hacerIntercambio(DatosIntercambio datosIntercambio);
    public Intercambio buscarReceptorDeAlgunIntercambio(PartidaUsuario partidaUsuario);
    public Intercambio buscarEmisorDeAlgunIntercambio(PartidaUsuario partidaUsuario);
}
