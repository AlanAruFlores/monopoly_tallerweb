package com.tallerwebi.dominio;

import com.tallerwebi.dominio.excepcion.*;
import java.util.List;
public interface ServicioLogin2 {

    Usuario consultarUsuario(String email, String password);
    void registrar(Usuario usuario) throws CamposIncompletosException,
            LongitudContraseñaException, MayusculaNumeroException, MailRegistradoException,
            ContraseñasNoCoincidenException, EmailInvalidoException;
    void agregarUsuario(Usuario dtousuario);
    public void banearUsuario(Long id);
    public List<Usuario> buscarTodos();
    public void cambiarEstadoBaneo(Long id);
}
