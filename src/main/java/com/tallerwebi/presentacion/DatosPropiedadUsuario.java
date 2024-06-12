package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Propiedad;
import com.tallerwebi.dominio.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DatosPropiedadUsuario {
    public Propiedad propiedad;
    public Usuario usuario;
    public String colorUsuario;
}
