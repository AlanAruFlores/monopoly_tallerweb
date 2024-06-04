package com.tallerwebi.dominio;
import com.tallerwebi.dominio.excepcion.ContraseniaInvalidaException;
import com.tallerwebi.dominio.excepcion.EmailInvalidoException;
import com.tallerwebi.dominio.excepcion.CamposVaciosException;

import com.tallerwebi.presentacion.DatosPerfil;
import org.springframework.web.multipart.MultipartFile;

public interface ServicioPerfil {
    public void actualizarPerfil(DatosPerfil datosPerfil) throws ContraseniaInvalidaException, EmailInvalidoException, CamposVaciosException;



    Usuario devolverUsuario(Long id);
}
