package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;

import com.tallerwebi.presentacion.DatosPerfil;

import javax.servlet.http.HttpSession;

public interface ServicioPerfil {
    public void actualizarPerfil(DatosPerfil datosPerfil, HttpSession session) throws ContraseniaInvalidaException, EmailInvalidoException, CamposVaciosException;
    Usuario devolverUsuario(Long id);
}
