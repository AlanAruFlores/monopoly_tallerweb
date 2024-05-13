package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioLogin2;
import com.tallerwebi.dominio.Usuario2;
import com.tallerwebi.dominio.excepcion.*;

import java.util.ArrayList;

public class ServicioLoginImpl2 implements ServicioLogin2 {
    private ArrayList<Usuario2> usuarios;

    public ServicioLoginImpl2() {
        this.usuarios = new ArrayList<>();
    }

    @Override
    public Usuario2 consultarUsuario(String email, String password) {
        for (Usuario2 usuario : usuarios) {
            if (usuario != null && usuario.getEmail().equals(email) && usuario.getPassword().equals(password)) {
                return usuario;
            }
        }
        return null;
    }

    public boolean isEmailLibre(String email) {
        for (Usuario2 usuario : usuarios) {
            if (usuario != null && usuario.getEmail().equals(email)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void registrar(Usuario2 usuario) throws CamposIncompletosException,
            LongitudContraseñaException, MayusculaNumeroException, MailRegistradoException,
            ContraseñasNoCoincidenException, EmailInvalidoException {
        // Verificar que todos los campos hayan sido completados
        if (usuario.getNombre() == null || usuario.getApellido() == null ||
                usuario.getEmail() == null || usuario.getNombreUsuario() == null ||
                usuario.getPassword() == null || usuario.getRepitePassword() == null) {
            throw new CamposIncompletosException();
        }

        // Verificar longitud de contraseña
        if (usuario.getPassword().length() < 5) {
            throw new LongitudContraseñaException();
        }

        // Verificar mayúscula y número en la contraseña
        if (!usuario.getPassword().matches(".*[A-Z].*") || !usuario.getPassword().matches(".*\\d.*")) {
            throw new MayusculaNumeroException();
        }

        // Verificar terminación de correo electrónico
        if (!usuario.getEmail().endsWith(".com")) {
            throw new EmailInvalidoException();
        }

        // Verificar si el email está libre
        if(!this.isEmailLibre(usuario.getEmail())){
            throw new MailRegistradoException();
        }

        // Verificar que las contraseñas coincidan
        if (!usuario.getPassword().equals(usuario.getRepitePassword())) {
            throw new ContraseñasNoCoincidenException();
        }
    }

    public void agregarUsuario(Usuario2 usuario) {
        usuarios.add(usuario);
    }
}
