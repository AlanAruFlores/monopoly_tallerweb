package com.tallerwebi.dominio.excepcion;

public class ContraseniaInvalidaException extends Throwable {
    public ContraseniaInvalidaException(String mensaje) {
        super(mensaje);
    }
}
