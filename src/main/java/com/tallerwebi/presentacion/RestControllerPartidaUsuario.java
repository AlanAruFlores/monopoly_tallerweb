package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.PartidaUsuario;
import com.tallerwebi.dominio.ServicioPartidaUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/partidausuario")
public class RestControllerPartidaUsuario {
    private ServicioPartidaUsuario servicioPartidaUsuario;

    @Autowired
    public RestControllerPartidaUsuario(ServicioPartidaUsuario servicioPartidaUsuario){
        this.servicioPartidaUsuario = servicioPartidaUsuario;
    }

    @RequestMapping(path ="/obtenerPorId", method= RequestMethod.GET)
    public PartidaUsuario obtenerPartidaUsuarioPorId(@RequestParam("id") Long id){
        return this.servicioPartidaUsuario.tenerPartidaUsuarioPorId(id);
    }
}
