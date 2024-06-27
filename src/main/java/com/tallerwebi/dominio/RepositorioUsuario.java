package com.tallerwebi.dominio;

import java.util.List;

public interface RepositorioUsuario {
    public void guardar(Usuario usuario);
    public Boolean existeUsuarioConEmail(String email);
    public void actualizarUsuario(Usuario usuario);
    public Usuario buscarUsuarioPorId(Long id);
    public boolean buscarUsuarioPorEmail(String email);
    public List<Usuario> buscarTodos();
}
