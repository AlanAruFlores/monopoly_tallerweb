package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;

import com.tallerwebi.presentacion.DatosPerfil;

public interface ServicioPerfil {
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseniaInvalidaException, EmailInvalidoException, CamposVaciosException;
}
