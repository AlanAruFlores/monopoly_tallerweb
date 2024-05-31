package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.excepcion.ExcesoDeJugadoresException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<Partida> obtenerTodasLasPartidas() {
        return this.repositorioPartida.obtenerPartidas();
    }

    @Override
    public void unirseAPartida(Long partidaId, Usuario usuario) throws ExcesoDeJugadoresException {
        /*Verifico la cantidad de jugadores en la partida con su limite*/
        Partida partidaBuscada = this.repositorioPartida.obtenerPartidaPorId(partidaId);
        Integer cantidadUsuariosEnLaPartida = this.repositorioPartidaUsuario.obtenerUsuariosEnUnaPartida(partidaId).size();

        if(cantidadUsuariosEnLaPartida >= partidaBuscada.getNumeroJugadores())
            throw new ExcesoDeJugadoresException();

        PartidaUsuario nuevoUsuarioEnLaPartida = new PartidaUsuario(null,partidaBuscada,usuario,0,1500.0,null);
        this.repositorioPartidaUsuario.crearPartidaUsuario(nuevoUsuarioEnLaPartida);
    }

    @Override
    public List<Usuario> verUsuariosEnlaPartidaEspera(Long partidaId){
        List<Usuario> usuariosEnLaPartida = this.repositorioPartidaUsuario.obtenerUsuariosEnUnaPartida(partidaId);
        return  usuariosEnLaPartida;
    }

}
