package com.tallerwebi.dominio;

import com.tallerwebi.infraestructura.ServicioRankingImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ServicioRankingTest {
    @Mock
    private RepositorioUsuario repositorioUsuario;

    @InjectMocks
    private ServicioRankingImpl servicioRanking;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindAllSortedByVictorias() {
        List<Usuario> usuariosEsperados = Arrays.asList(new Usuario("user2","mx@m.com","12345M","dx","x","12345M"), new Usuario("user2","mx@m.com","12345M","dx","x","12345M"));
        when(repositorioUsuario.mostrarUsuariosPorVictorias()).thenReturn(usuariosEsperados);

        List<Usuario> usuariosActuales = servicioRanking.findAllSortedByVictorias();

        assertEquals(usuariosEsperados, usuariosActuales);
        verify(repositorioUsuario).mostrarUsuariosPorVictorias();
    }
}