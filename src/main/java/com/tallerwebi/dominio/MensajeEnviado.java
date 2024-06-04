package com.tallerwebi.dominio;

public class MensajeEnviado {

    private String message;

    public MensajeEnviado(){}

    public MensajeEnviado(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
