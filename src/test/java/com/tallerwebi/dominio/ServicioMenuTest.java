package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ServicioMenuImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ServicioMenuTest {

    private ServicioMenu servicioMenu;
    private RepositorioPartidaUsuario repositorioPartidaUsuario;

    @BeforeEach
    void init(){
        repositorioPartidaUsuario = mock(RepositorioPartidaUsuario.class);
        servicioMenu = new ServicioMenuImpl(repositorioPartidaUsuario);
    }

    public Usuario givenUsuarioLogeado(){
        return new Usuario();
    }

    @Test
    public void verSiTienenPartidaPendienteUnUsuario(){
        Usuario usuario = givenUsuarioLogeado();
        when(this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorUsuarioId(usuario.getId()))
                .thenReturn(new PartidaUsuario());
        thenTieneUnaPartidaEnPendiente(usuario);
    }

    public void thenTieneUnaPartidaEnPendiente(Usuario usuario){
        assertThat(this.servicioMenu.verSiTieneUnaPartidaEnCursoPorUsuario(usuario), notNullValue());
    }

    @Test
    public void verSiNoTieneUnaPartidaPendiente(){
        Usuario usuario = givenUsuarioLogeado();
        when(this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorUsuarioId(usuario.getId()))
                .thenReturn(null);
        thenNoTieneUnaPartidaPendiente(usuario);
    }

    public void thenNoTieneUnaPartidaPendiente(Usuario usuario){
        assertThat(this.servicioMenu.verSiTieneUnaPartidaEnCursoPorUsuario(usuario), nullValue());
    }
}
