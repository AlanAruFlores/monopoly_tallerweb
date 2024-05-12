package com.tallerwebi.dominio;

public class Propiedad {
    private Integer id;
    private String propiedad;
    private String imagen;
    private Integer precio;

    public Propiedad(){}

    public Propiedad(Integer id, Integer precio, String imagen,String propiedad) {
        this.id = id;
        this.precio = precio;
        this.propiedad = propiedad;
        this.imagen = imagen;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPropiedad() {
        return propiedad;
    }

    public void setPropiedad(String propiedad) {
        this.propiedad = propiedad;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }
}
