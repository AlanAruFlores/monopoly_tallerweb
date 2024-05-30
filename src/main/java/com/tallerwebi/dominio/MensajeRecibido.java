package com.tallerwebi.dominio;

public class MensajeRecibido {

    private Integer idEmisor;
    private String message;

    public MensajeRecibido(){}

    public MensajeRecibido(Integer idEmisor,String message){
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
