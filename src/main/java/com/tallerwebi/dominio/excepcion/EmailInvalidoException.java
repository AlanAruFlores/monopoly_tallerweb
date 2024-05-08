package com.tallerwebi.dominio.excepcion;

public class EmailInvalidoException extends Throwable {
    public EmailInvalidoException(String mensaje) {
        super(mensaje);
    }
}
