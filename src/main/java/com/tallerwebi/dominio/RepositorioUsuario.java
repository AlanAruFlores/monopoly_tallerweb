package com.tallerwebi.dominio;

public interface RepositorioUsuario {
    public void guardar(Usuario usuario);
    public Boolean existeUsuarioConEmail(String email);
    public void actualizarUsuario(Usuario usuario);
    public Usuario buscarUsuarioPorId(Long id);
    public boolean buscarUsuarioPorEmail(String email);
}
