package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ServicioPartidaImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import javax.servlet.http.Part;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioPartidaTest {

    private ServicioPartida servicioPartida;
    private RepositorioPartidaUsuario repositorioPartidaUsuario;
    private RepositorioPartida repositorioPartida;

    @BeforeEach
    void init(){
        this.repositorioPartidaUsuario = mock(RepositorioPartidaUsuario.class);
        this.repositorioPartida = mock(RepositorioPartida.class);
        this.servicioPartida = new ServicioPartidaImpl(repositorioPartida, repositorioPartidaUsuario);
    }

    @Test
    public void queSeObtengaPartidaPorCreador(){
        Usuario usuario = givenUsuarioExistente();
        when(this.repositorioPartida.obtenerPartidaPorCreador(usuario)).thenReturn(new Partida());

        Partida partida = whenObtenerPartidaCreador(usuario);

        thenPartidaExiste(partida);
        
    }

    private void thenPartidaExiste(Partida partida) {
        assertThat(partida, notNullValue());
    }

    private Partida whenObtenerPartidaCreador(Usuario usuario) {
        return  this.servicioPartida.obtenerPartidaPorCreador(usuario);
    }

    private Usuario givenUsuarioExistente(){
        return new Usuario();
    }


    @Test
    public void queSeObtengaTodasLasPartidas(){
        List<Partida> partidas = givenPartidasList();
        when(this.repositorioPartida.obtenerPartidas()).thenReturn(partidas);
        thenExistePartidas(partidas, 2);
    }
    private List<Partida> givenPartidasList(){
        List<Partida> partidas = new ArrayList<>();
        partidas.add(givenPartidaExistente(1L));
        partidas.add(givenPartidaExistente(2L));
        return partidas;
    }
    private Partida givenPartidaExistente(Long id){
        Partida partida = new Partida();
        partida.setId(id);
        return partida;
    }
    private void thenExistePartidas(List<Partida> partidas, Integer numPartida){
        assertThat(partidas.isEmpty(), equalTo(false));
        assertThat(partidas.size(), equalTo(numPartida));
    }

    @Test
    public void queSeTengaPartidaPorPartidaId(){
        Partida partida = givenPartidaExistente(1L);
        when(this.repositorioPartida.obtenerPartidaPorId(1L)).thenReturn(partida);
        thenPartidaExiste(partida);
    }
}
