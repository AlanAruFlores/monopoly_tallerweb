package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Intercambio;
import com.tallerwebi.dominio.IntercambioPropiedades;
import com.tallerwebi.dominio.RepositorioIntercambioPropiedad;
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
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioIntercambioPropiedadesTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioIntercambioPropiedad repositorioIntercambioPropiedad;

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queSePuedaObtenerIntercambioPropiedadesPorIntercambio(){
        Intercambio intercambio = givenIntercambio(1L);
        givenIntercambioPropiedad(intercambio);

        List<IntercambioPropiedades> ip = whenObtenerIntercambioPropiedadPorIntercambio(intercambio);

        thenExisteIntercambioPropiedades(ip);

    }

    private List<IntercambioPropiedades> whenObtenerIntercambioPropiedadPorIntercambio(Intercambio intercambio) {
        return this.repositorioIntercambioPropiedad.obtenerIntercambioPropiedadesPorIntercambio(intercambio);
    }

    private Intercambio givenIntercambio(Long id){
        Intercambio intercambio = new Intercambio();
        intercambio.setId(id);
        this.sessionFactory.getCurrentSession().save(intercambio);
        return intercambio;
    }

    private void givenIntercambioPropiedad(Intercambio intercambio){
        IntercambioPropiedades ip  = new IntercambioPropiedades();
        ip.setIntercambio(intercambio);
        this.sessionFactory.getCurrentSession().save(ip);
    }

    private void thenExisteIntercambioPropiedades(List<IntercambioPropiedades> intercambioPropiedades){
        assertThat(intercambioPropiedades.isEmpty(), equalTo(false));
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queNOSePuedaObtenerIntercambioPropiedadesPorIntercambio(){
        Intercambio intercambio = givenIntercambio(1L);
        givenIntercambioPropiedad(givenIntercambio(2L));

        List<IntercambioPropiedades> ip = whenObtenerIntercambioPropiedadPorIntercambio(intercambio);

        thenNoExisteIntercambioPropiedades(ip);

    }


    private void thenNoExisteIntercambioPropiedades(List<IntercambioPropiedades> intercambioPropiedades){
        assertThat(intercambioPropiedades.isEmpty(), equalTo(true));
    }
}
