package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;

public interface ServicioLogin2 {

    Usuario2 consultarUsuario(String email, String password);
    void registrar(Usuario2 usuario) throws CamposIncompletosException,
            LongitudContraseñaException, MayusculaNumeroException, MailRegistradoException,
            ContraseñasNoCoincidenException, EmailInvalidoException;
    void agregarUsuario(Usuario2 dtousuario);
}
