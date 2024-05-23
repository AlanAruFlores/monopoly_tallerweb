package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

public interface ServicioLogin2 {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws CamposIncompletosException,
            LongitudContraseñaException, MayusculaNumeroException, MailRegistradoException,
            ContraseñasNoCoincidenException, EmailInvalidoException;
    void agregarUsuario(Usuario dtousuario);
}
