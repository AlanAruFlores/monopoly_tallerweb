package com.tallerwebi.presentacion;

import com.tallerwebi.config.InterceptorAuthentication;
import com.tallerwebi.dominio.Usuario;
import com.tallerwebi.integracion.config.HibernateTestConfig;
import com.tallerwebi.integracion.config.SpringWebTestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = {SpringWebTestConfig.class, HibernateTestConfig.class})
public class InterceptorTest {

    @Autowired
    private RequestMappingHandlerMapping mapping;

    @Test
    public void queElInterceptorAuthenticacionIntercepteUnaPeticion() throws Exception {
        MockHttpServletRequest request = new MockHttpServletRequest("GET","/ir-menu");
        HandlerExecutionChain chain = mapping.getHandler(request);

        MockHttpSession session = new MockHttpSession();
        session.setAttribute("usuarioLogeado", null);

        assertThat(chain, notNullValue());

        Optional<HandlerInterceptor> interceptorAuthentication = Arrays.stream(chain.getInterceptors())
                .filter(InterceptorAuthentication.class :: isInstance)
                .findFirst();

        System.out.println(chain.getInterceptors());
        System.out.println(chain.getInterceptors());
        assertThat(interceptorAuthentication.isPresent() , equalTo(false));
    }

}
