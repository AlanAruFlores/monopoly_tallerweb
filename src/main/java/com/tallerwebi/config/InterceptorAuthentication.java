package com.tallerwebi.config;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class InterceptorAuthentication implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();


        if(session.getAttribute("usuarioLogeado")==null) {
            String mensaje = "No está autorizado para acceder a esta página. Por favor, inicie sesión para continuar.";
            Integer codigo = HttpServletResponse.SC_UNAUTHORIZED;
            response.sendRedirect(request.getContextPath()+"/error/?codigo="+ codigo+"&mensaje="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString()));
            return false;
        }

        return true;
    }

}
