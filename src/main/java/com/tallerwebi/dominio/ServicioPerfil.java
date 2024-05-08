package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.ContraseñaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.presentacion.DatosPerfil;

public interface ServicioPerfil {
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseñaInvalidaException, EmailInvalidoException;
}
