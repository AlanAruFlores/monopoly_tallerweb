package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcesoDeJugadoresException;
import com.tallerwebi.dominio.excepcion.UsuarioNoAutenticadoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;
import javax.transaction.Transactional;
import java.util.List;

@Service("servicioPartida")
@Transactional
public class ServicioPartidaImpl implements ServicioPartida {

    private RepositorioPartida  repositorioPartida;
    private RepositorioPartidaUsuario repositorioPartidaUsuario;

    @Autowired
    public ServicioPartidaImpl(RepositorioPartida repositorioPartida, RepositorioPartidaUsuario repositorioPartidaUsuario) {
        this.repositorioPartida = repositorioPartida;
        this.repositorioPartidaUsuario = repositorioPartidaUsuario;
    }

    @Override
    public void crearUnaPartidaNueva(Partida partida) {
        this.repositorioPartida.crearPartida(partida);
    }

    @Override
    public void actualizarEstadoDeUnaPartida(Partida partida, EstadoPartida estadoPartida) {
        partida.setEstadoPartida(estadoPartida);
        this.repositorioPartida.actualizarPartida(partida);
    }

    @Override
    public Partida obtenerPartidaPorCreador(Usuario creador){
        return this.repositorioPartida.obtenerPartidaPorCreador(creador);
    }

    @Override
    public List<Partida> obtenerTodasLasPartidas() {
        return this.repositorioPartida.obtenerPartidas();
    }

    @Override
    public Partida obtenerPartidaPorPartidaId(Long partidaId) {
        return this.repositorioPartida.obtenerPartidaPorId(partidaId);
    }

    @Override
    public void unirseAPartida(Partida partidaAUnirse, Usuario usuario) throws ExcesoDeJugadoresException {
        /*Verifico la cantidad de jugadores en la partida con su limite*/
        Integer cantidadUsuariosEnLaPartida = this.repositorioPartidaUsuario.obtenerUsuariosEnUnaPartida(partidaAUnirse.getId()).size();

        if(cantidadUsuariosEnLaPartida >= partidaAUnirse.getNumeroJugadores())
            throw new ExcesoDeJugadoresException();

        /*Evaluo el color que le pondremos al auto*/

        List<Color> coloresUsadosPorUsuarios = this.repositorioPartidaUsuario.obtenerColoresJugadoresUsuados(partidaAUnirse.getId());
        Color[] coloresDisponibles = Color.values();

        Color colorSeleccionado = null;
        for(Color c: coloresDisponibles){
            if(!coloresUsadosPorUsuarios.contains(c)){
                colorSeleccionado = c;
                break;
            }
        }

        PartidaUsuario nuevoUsuarioEnLaPartida = new PartidaUsuario(null,partidaAUnirse,usuario,1,1500.0,colorSeleccionado,EstadoActividad.ACTIVO,null);
        this.repositorioPartidaUsuario.crearPartidaUsuario(nuevoUsuarioEnLaPartida);
    }

    @Override
    public Partida actualizarEstadoDeLaPartida(Partida partidaActualizada, EstadoPartida estadoNuevo) {
        partidaActualizada.setEstadoPartida(estadoNuevo);
        this.repositorioPartida.actualizarPartida(partidaActualizada);
        return partidaActualizada;
    }

    @Override
    public List<Usuario> verUsuariosEnlaPartidaEspera(Long partidaId){
        List<Usuario> usuariosEnLaPartida = this.repositorioPartidaUsuario.obtenerUsuariosEnUnaPartida(partidaId);
        return  usuariosEnLaPartida;
    }


    @Override
    public Usuario obtenerCreadoUsuarioDeUnaPartida(Long partidaId){
        Usuario usuarioCreador= this.repositorioPartida.obtenerPartidaPorId(partidaId).getCreador();
        return usuarioCreador;
    }

    @Override
    public void salirDeLaPartida(Long partidaId, Long usuarioId) {
        this.repositorioPartidaUsuario.eliminarPartidaUsuario(partidaId,usuarioId);
    }

    @Override
    public PartidaUsuario verSiTieneUnaPartidaEnCursoPorUsuario(Usuario usuarioActual){
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorUsuarioId(usuarioActual.getId());
    }

    @Override
    public void eliminarPartida(Long partidaId) {
        this.repositorioPartida.eliminarPartidaPorId(partidaId);
    }
}
