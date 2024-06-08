package com.tallerwebi.presentacion;

import com.tallerwebi.dominio.Partida;
import com.tallerwebi.dominio.ServicioPartida;
import com.tallerwebi.dominio.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuario")
public class RestControladorUsuario {
    private ServicioPartida servicioPartida;

    @Autowired
    public RestControladorUsuario(ServicioPartida servicioPartida){
        this.servicioPartida = servicioPartida;
    }

    @RequestMapping(path="/obtenerUsuarioEnLaPartida",method= RequestMethod.GET)
    public List<Usuario> obtenerUsuariosEnlaPartida(@RequestParam("id") Long partidaId){
        Partida partidaEnJuego = this.servicioPartida.obtenerPartidaPorPartidaId(partidaId);
        return this.servicioPartida.verUsuariosEnlaPartidaEspera(partidaEnJuego.getId());
    }

}
