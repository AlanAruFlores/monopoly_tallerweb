package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.PartidaUsuarioPropiedad;
import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.RepositorioPartidaUsuarioPropiedad;
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

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class RepositorioPartidaUsuarioPropiedadTest {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private RepositorioPartidaUsuarioPropiedad repositorioPartidaUsuarioPropiedad;


    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void quePuedaObtenerPartidaUsuarioPropiedadPorId(){
        givenPartidaUsuarioPropiedad(1, givenPropiedad(1L));
        PartidaUsuarioPropiedad partidaUsuarioPropiedad = whenObtenerPartidaUsuariosPropiedadesPorId(1);

        thenExistePartidaUsuarioPropiedad(partidaUsuarioPropiedad);
    }

    private void thenExistePartidaUsuarioPropiedad(PartidaUsuarioPropiedad partidaUsuarioPropiedad) {
        assertThat(partidaUsuarioPropiedad, notNullValue());
    }

    private void thenNoExistePartidaUsuarioPropiedad(PartidaUsuarioPropiedad partidaUsuarioPropiedad) {
        assertThat(partidaUsuarioPropiedad, nullValue());
    }

    private PartidaUsuarioPropiedad whenObtenerPartidaUsuariosPropiedadesPorId(Integer id) {
        return this.repositorioPartidaUsuarioPropiedad.obtenerPartidaUsuarioPropiedadPorId(id);
    }

    private Propiedad givenPropiedad(Long id){
        Propiedad prop  = new Propiedad();
        prop.setId(id);
        this.sessionFactory.getCurrentSession().save(prop);
        return prop;
    }

    private void givenPartidaUsuarioPropiedad(Integer idPup, Propiedad propiedad){
        PartidaUsuarioPropiedad pup = new PartidaUsuarioPropiedad();
        pup.setId(idPup);
        pup.setPropiedad(propiedad);
        this.sessionFactory.getCurrentSession().save(pup);
    }

    @Test
    @Transactional
    @Rollback
    @DirtiesContext
    public void queNOPuedaObtenerPartidaUsuarioPropiedadPorId(){
        givenPartidaUsuarioPropiedad(1, givenPropiedad(1L));
        PartidaUsuarioPropiedad partidaUsuarioPropiedad = whenObtenerPartidaUsuariosPropiedadesPorId(2);

        thenNoExistePartidaUsuarioPropiedad(partidaUsuarioPropiedad);
    }

}
