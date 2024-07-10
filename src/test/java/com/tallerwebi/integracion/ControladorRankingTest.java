package com.tallerwebi.integracion;

import com.tallerwebi.dominio.ServicioRanking;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.presentacion.ControladorRanking;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ControladorRankingTest {
    @Mock
    private ServicioRanking servicioRanking;

    @InjectMocks
    private ControladorRanking controladorRanking;

    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controladorRanking).build();
    }

    @Test
    public void testRanking() throws Exception {
        List<Usuario> usuarios = Arrays.asList(new Usuario("user1","m@m.com","12345M","d","s","12345M"), new Usuario("user2","mx@m.com","12345M","dx","x","12345M"));
        when(servicioRanking.findAllSortedByVictorias()).thenReturn(usuarios);

        mockMvc.perform(get("/ranking"))
                .andExpect(status().isOk())
                .andExpect(view().name("ranking.html"))
                .andExpect(model().attribute("usuarios", usuarios));
    }

    @Test
    public void testIrAMenu() throws Exception {
        mockMvc.perform(get("/volver-menu"))
                .andExpect(status().isOk())
                .andExpect(view().name("menu.html"));
    }
}
