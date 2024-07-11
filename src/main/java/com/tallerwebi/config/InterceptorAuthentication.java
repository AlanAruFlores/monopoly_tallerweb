package com.tallerwebi.config;
import com.tallerwebi.dominio.Usuario;
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

        String encabezadoPostman = request.getHeader("EncabezadoPostman");
        if ("disable-interceptor".equals(encabezadoPostman)) {
            return true;
        }

        if(session.getAttribute("usuarioLogeado")==null) {
            String mensaje = "No está autorizado para acceder a esta página. Por favor, inicie sesión para continuar.";
            Integer codigo = HttpServletResponse.SC_UNAUTHORIZED;
            response.sendRedirect(request.getContextPath()+"/error/?codigo="+ codigo+"&mensaje="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString()));
            return false;
        }

        Usuario usuario  = (Usuario) session.getAttribute("usuarioLogeado");
        if(request.getRequestURI().equalsIgnoreCase("/spring/ir-menu-admin") && usuario.getId() != 1){
            String mensaje = "No tiene permiso para acceder esta sesion.";
            Integer codigo = HttpServletResponse.SC_UNAUTHORIZED;
            response.sendRedirect(request.getContextPath()+"/error/?codigo="+ codigo+"&mensaje="+ URLEncoder.encode(mensaje, StandardCharsets.UTF_8.toString()));
            return false;
        }

        return true;
    }

}
