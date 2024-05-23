package com.tallerwebi.dominio;

public interface RepositorioUsuario {
    public void guardar(Usuario usuario);
    public Boolean existeUsuarioConEmail(String email);
}
