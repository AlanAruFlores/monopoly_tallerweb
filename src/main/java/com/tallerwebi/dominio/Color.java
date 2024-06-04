package com.tallerwebi.dominio;


public enum Color {
    ROJO("#f80000"),
    VERDE("#258d19"),
    AZUL("#107acc"),
    ROSA("#d5408e");

    private String codigoHexadecimal;
    private Color(String codigoHexadecimal) {
        this.codigoHexadecimal = codigoHexadecimal;
    }
    public String getCodigoHexadecimal() {
        return this.codigoHexadecimal;
    }
}
