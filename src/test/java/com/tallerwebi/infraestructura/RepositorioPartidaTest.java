package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.RepositorioPartida;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.hamcrest.Matchers;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.List;

import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPartidaTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioPartida repositorioPartida;

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerPartidas(){
        givenPartidaExistente(1L,null);
        givenPartidaExistente(2L,null);
        givenPartidaExistente(3L,null);

        List<Partida> partidas = whenObtenerPartidas();

        thenExistePartidas(partidas, 3);
    }

    private List<Partida> whenObtenerPartidas() {
        return repositorioPartida.obtenerPartidas();
    }

    private void givenPartidaExistente(Long id, Usuario creador) {
        Partida partida = new Partida();
        partida.setId(id);
        partida.setCreador(creador);
        sessionFactory.getCurrentSession().save(partida);
    }

    private void thenExistePartidas(List<Partida> partidas, Integer cantidad) {
        assertThat(partidas.isEmpty(), equalTo(false));
        assertThat(partidas.size(), equalTo(cantidad));
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerPartidaPorId(){
        givenPartidaExistente(1L,null);
        givenPartidaExistente(2L,null);
        givenPartidaExistente(3L,null);

        Partida partida = whenObtenerPartidaPorId(1L);

        thenExistePartida(partida);
    }

    private Partida whenObtenerPartidaPorId(Long id) {
        return this.repositorioPartida.obtenerPartidaPorId(id);
    }

    private void thenExistePartida(Partida partida) {
        assertThat(partida,notNullValue());
    }


    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerPartidaPorCreador(){
        Usuario creador = givenUsuarioExistente();
        givenPartidaExistente(1L,creador);
        givenPartidaExistente(2L,null);
        givenPartidaExistente(3L,null);

        Partida partidaPorCreador = this.repositorioPartida.obtenerPartidaPorCreador(creador);

        thenExistePartida(partidaPorCreador);
    }

    private Usuario givenUsuarioExistente(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }
}
