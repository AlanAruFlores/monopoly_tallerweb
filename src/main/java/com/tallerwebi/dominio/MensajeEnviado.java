package com.tallerwebi.dominio;

public class MensajeEnviado {

    private Integer idEmisor;

    private String message;

    public MensajeEnviado(){}

    public MensajeEnviado(Integer idEmisor, String message){
        this.message = message;
        this.idEmisor = idEmisor;
    }

    public Integer getIdEmisor() {
        return idEmisor;
    }

    public void setIdEmisor(Integer idEmisor) {
        this.idEmisor = idEmisor;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
