package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Intercambio;
import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.RepositorioIntercambio;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioIntercambioTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioIntercambio repositorioIntercambio;

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerUnaIntercambioPorId() {
        givenIntercambioExistente(1L,null,null);
        Intercambio intercambio = whenObtenerIntercambioPorId(1L);
        thenExisteElIntercambio(intercambio);
    }

    private void givenIntercambioExistente(Long id, PartidaUsuario emisor, PartidaUsuario receptor){
        Intercambio intercambio = new Intercambio();
        intercambio.setId(id);
        intercambio.setEmisor(emisor);
        intercambio.setReceptor(receptor);
        this.sessionFactory.getCurrentSession().save(intercambio);
    }

    private Intercambio  whenObtenerIntercambioPorId(Long id){
        return this.repositorioIntercambio.getIntercambioPorId(id);
    }

    private void thenExisteElIntercambio(Intercambio intercambio){
        assertThat(intercambio, notNullValue());
    }
    private void thenNoExisteElIntercambio(Intercambio intercambio){
        assertThat(intercambio, nullValue());
    }
    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queNOSePuedaObtenerUnaIntercambioPorId() {
        givenIntercambioExistente(1L,null,null);
        Intercambio intercambio = whenObtenerIntercambioPorId(2L);
        thenNoExisteElIntercambio(intercambio);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSeBusqueIntercambioByEmisorId(){
        PartidaUsuario p1 =givenPartidaUsuarioExistente(1L,1L),
                p2 = givenPartidaUsuarioExistente(1L,2L),
                p3 =  givenPartidaUsuarioExistente(1L,3L);
        givenIntercambioExistente(1L, p1,null);
        givenIntercambioExistente(1L, p2,null);
        givenIntercambioExistente(1L,p3,null);

        Intercambio intercambio = whenObtenerIntercambioPorEmisor(p3);
        thenExisteElIntercambio(intercambio);
    }

    private Intercambio whenObtenerIntercambioPorEmisor(PartidaUsuario emisor) {
        return this.repositorioIntercambio.buscarIntercambioByEmisorId(emisor);
    }

    private PartidaUsuario givenPartidaUsuarioExistente(Long id, Long idUsuario){
        PartidaUsuario usuarioEnPartida =  new PartidaUsuario();
        usuarioEnPartida.setId(id);
        usuarioEnPartida.setUsuario(givenUsuarioExistente(idUsuario));
        this.sessionFactory.getCurrentSession().save(usuarioEnPartida);
        return usuarioEnPartida;
    }

    private Usuario givenUsuarioExistente(Long id){
        Usuario usuario = new Usuario();
        usuario.setId(id);
        this.sessionFactory.getCurrentSession().save(usuario);
        return usuario;
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queNOSeBusqueIntercambioByEmisorId(){
        PartidaUsuario p1 =givenPartidaUsuarioExistente(1L,1L),
                p2 = givenPartidaUsuarioExistente(1L,2L),
                p3 =  givenPartidaUsuarioExistente(1L,3L);
        givenIntercambioExistente(1L, p1,null);
        givenIntercambioExistente(1L, p2,null);

        Intercambio intercambio = whenObtenerIntercambioPorEmisor(p3);
        thenNoExisteElIntercambio(intercambio);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSeBusqueIntercambioByDestinatarioId(){
        PartidaUsuario p1 =givenPartidaUsuarioExistente(1L,1L),
                p2 = givenPartidaUsuarioExistente(1L,2L),
                p3 = givenPartidaUsuarioExistente(1L, 3L);

        givenIntercambioExistente(1L, p1, p2);

        Intercambio intercambio = whenObtenerIntercambioPorReceptor(p2);
        thenExisteElIntercambio(intercambio);
    }

    private Intercambio whenObtenerIntercambioPorReceptor(PartidaUsuario receptor){
        return this.repositorioIntercambio.buscarIntercambioByDestinatarioId(receptor);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queNOSeBusqueIntercambioByDestinatarioId(){
        PartidaUsuario p1 =givenPartidaUsuarioExistente(1L,1L),
                p2 = givenPartidaUsuarioExistente(1L,2L),
                p3 = givenPartidaUsuarioExistente(1L, 3L);

        givenIntercambioExistente(1L, p1, p2);

        Intercambio intercambio = whenObtenerIntercambioPorReceptor(p3);
        thenNoExisteElIntercambio(intercambio);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSeBusqueIntercambioByDestinatarioYEmisor(){
        PartidaUsuario p1 =givenPartidaUsuarioExistente(1L,1L),
                p2 = givenPartidaUsuarioExistente(1L,2L),
                p3 = givenPartidaUsuarioExistente(1L, 3L);

        givenIntercambioExistente(1L, p1, p2);

        Intercambio intercambio = whenObtenerIntercambioPorEmisorReceptor(p1,p2);
        thenExisteElIntercambio(intercambio);
    }

    private Intercambio whenObtenerIntercambioPorEmisorReceptor(PartidaUsuario emisor, PartidaUsuario receptor) {
        return this.repositorioIntercambio.buscarIntercambioByEmisorIdAndDestinatarioId(emisor.getId(), receptor.getId());
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queNOSeBusqueIntercambioByDestinatarioYEmisor(){
        PartidaUsuario p1 =givenPartidaUsuarioExistente(1L,1L),
                p2 = givenPartidaUsuarioExistente(1L,2L),
                p3 = givenPartidaUsuarioExistente(1L, 3L);

        givenIntercambioExistente(1L, p3, p2);

        Intercambio intercambio = whenObtenerIntercambioPorEmisorReceptor(p1,p2);
        thenNoExisteElIntercambio(intercambio);
    }

}
