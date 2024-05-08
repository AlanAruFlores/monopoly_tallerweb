package com.tallerwebi.dominio.excepcion;

public class ContraseñaInvalidaException extends Throwable {
    public ContraseñaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
