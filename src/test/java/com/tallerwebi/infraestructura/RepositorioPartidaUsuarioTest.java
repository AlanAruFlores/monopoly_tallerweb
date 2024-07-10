package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIfSystemProperty;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.servlet.http.Part;
import javax.transaction.Transactional;

import java.util.List;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.annotation.DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPartidaUsuarioTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioPartidaUsuario repositorioPartidaUsuario;

    private Usuario givenUsuarioExistente(Long id) {
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(id);
        sessionFactory.getCurrentSession().save(usuarioExistente);
        return usuarioExistente;
    }

    private Partida givenPartidaExistente(Long id) {
        Partida partidaExistente = new Partida();
        partidaExistente.setId(id);
        sessionFactory.getCurrentSession().save(partidaExistente);
        return partidaExistente;
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerPartidaUsuarioPorUsuarioId() {
        givenPartidaUsuarioExiste(null, givenUsuarioExistente(1L),1L, Color.AZUL);
        PartidaUsuario pu = whenObtenerPartidaUsuario(1L);
        thenExisteLaPartidaUsuario(pu);
    }

    private void thenExisteLaPartidaUsuario(PartidaUsuario pu) {
        assertThat(pu, notNullValue());
    }

    private PartidaUsuario whenObtenerPartidaUsuario(Long id) {
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorUsuarioId(id);
    }

    private void givenPartidaUsuarioExiste(Partida partida, Usuario usuario, Long id, Color color) {
        PartidaUsuario pu = new PartidaUsuario();
        pu.setId(id);
        pu.setUsuario(usuario);
        pu.setPartida(partida);
        pu.setColorUsuario(color);
        sessionFactory.getCurrentSession().save(pu);

    }


    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerUsuarioPartidaPorPartidaIdYUsuarioId() {
        givenPartidaUsuarioExiste(givenPartidaExistente(1L), givenUsuarioExistente(1L),1L, Color.AZUL);
        PartidaUsuario pu = whenObtenerPartidaUsuarioPorPartidaIdYUsuarioId(1L, 1L);
        thenExisteLaPartidaUsuario(pu);
    }

    private PartidaUsuario whenObtenerPartidaUsuarioPorPartidaIdYUsuarioId(Long idPartida, Long idUsuario) {
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(idPartida, idUsuario);
    }


    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerPartidasUsuariosEnlaPartidaId(){
        Partida partida = givenPartidaExistente(1L);

        givenPartidaUsuarioExiste(partida, givenUsuarioExistente(1L),1L,Color.AZUL);
        givenPartidaUsuarioExiste(partida, givenUsuarioExistente(2L),2L,Color.ROJO);
        givenPartidaUsuarioExiste(partida, givenUsuarioExistente(3L),3L,Color.ROSA);

        List<PartidaUsuario> listaPartidasUsuarios = whenObtenerPartidasUsuariosEnLaPartidaId(1L);

        thenExistePartidasUsuarios(listaPartidasUsuarios);
    }

    private List<PartidaUsuario> whenObtenerPartidasUsuariosEnLaPartidaId(Long idPartida){
        return this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(idPartida);
    }

    private void thenExistePartidasUsuarios(List<PartidaUsuario> listaPartidasUsuarios) {
        assertThat(listaPartidasUsuarios.size(), equalTo(3));
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerUsuarioPartidaPorId(){
        givenPartidaUsuarioExiste(givenPartidaExistente(1L), givenUsuarioExistente(1L),1L, Color.AZUL);
        PartidaUsuario pu = whenObtenerPartidaUsuarioPorSuId(1L);
        thenExisteLaPartidaUsuario(pu);
    }

    private PartidaUsuario whenObtenerPartidaUsuarioPorSuId(Long id){
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorId(id);
    }


    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerColoresJugadoresUsados(){
        Partida partida = givenPartidaExistente(1L);

        givenPartidaUsuarioExiste(partida, givenUsuarioExistente(1L),1L,Color.AZUL);
        givenPartidaUsuarioExiste(partida, givenUsuarioExistente(2L),2L,Color.ROJO);
        givenPartidaUsuarioExiste(partida, givenUsuarioExistente(3L),3L,Color.ROSA);

        List<Color> colores = whenObtenerColoresUsadosPorPartidaId(1L);

        thenHayColores(colores, 3);
    }

    private List<Color> whenObtenerColoresUsadosPorPartidaId(Long idPartida){
        return this.repositorioPartidaUsuario.obtenerColoresJugadoresUsuados(idPartida);
    }

    private void thenHayColores(List<Color> colores, Integer cantidad){
        assertThat(colores.isEmpty(), equalTo(false));
        assertThat(colores.size(), equalTo(cantidad));
    }
}
