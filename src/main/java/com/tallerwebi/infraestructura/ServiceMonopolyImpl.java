package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.*;
import com.tallerwebi.dominio.RepositorioPartidaUsuarioPropiedad;
import com.tallerwebi.dominio.excepcion.SaldoInsuficienteException;
import com.tallerwebi.dominio.excepcion.UsuarioPerdedorException;
import com.tallerwebi.presentacion.DatosPagarPropiedad;
import com.tallerwebi.presentacion.DatosPropiedadUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service("servicioMonopoly")
@Transactional
public class ServiceMonopolyImpl implements ServicioMonopoly{
    private RepositorioPartidaUsuario repositorioPartidaUsuario;
    private RepositorioPartida repositorioPartida;
    private RepositorioPropiedad repositorioPropiedad;
    private RepositorioPartidaUsuarioPropiedad repositorioPartidaUsuarioPropiedad;
    /*Enlace de Dados y Su numero*/
    Map<Integer, String> mapaDado = new HashMap<Integer, String>();
    @Autowired
    public ServiceMonopolyImpl(RepositorioPartidaUsuario repositorioPartidaUsuario, RepositorioPartida repositorioPartida, RepositorioPropiedad repositorioPropiedad, RepositorioPartidaUsuarioPropiedad repositorioPartidaUsuarioPropiedad) {
        this.repositorioPartidaUsuario = repositorioPartidaUsuario;
        this.repositorioPartida = repositorioPartida;
        this.repositorioPropiedad = repositorioPropiedad;
        this.repositorioPartidaUsuarioPropiedad = repositorioPartidaUsuarioPropiedad;

        /*Llenamos datos al mapa */
        mapaDado.put(1,"/imagenes/dados/dado1.png");
        mapaDado.put(2,"/imagenes/dados/dado2.png");
        mapaDado.put(3,"/imagenes/dados/dado3.png");
        mapaDado.put(4,"/imagenes/dados/dado4.png");
        mapaDado.put(5,"/imagenes/dados/dado5.png");
        mapaDado.put(6,"/imagenes/dados/dado6.png");
    }


    private Integer obtenerPosicionCasillero(Integer posicionUsuario,HttpSession session) {
        Integer posicionObtenida = (int) (1 + (Math.random() * (6 - 1)));
        session.setAttribute("dado",mapaDado.get(posicionObtenida));
        posicionObtenida += posicionUsuario;
        if(posicionObtenida > 20)
            posicionObtenida = (posicionObtenida-20) ;

        return posicionObtenida;
    }

    @Override
    public void moverJugadorAlCasillero(PartidaUsuario usuarioAMover,HttpSession session) throws UsuarioPerdedorException{
        Integer posicionObtenida = obtenerPosicionCasillero(usuarioAMover.getPosicionCasilla(),session);
        //Determino si piso en alguna propiedad
        Propiedad propiedadEnLaCasilla = determinarSiPisoEnAlgunaPropiedadDisponible(posicionObtenida, usuarioAMover.getPartida());

        if(propiedadEnLaCasilla != null)
            session.setAttribute("propiedad",propiedadEnLaCasilla);
        else{
            //Busco si le pertenece a alguien
            PartidaUsuarioPropiedad pup = verSiLaPropiedadLePerteneceAAlguien(posicionObtenida, usuarioAMover.getUsuario(), usuarioAMover.getPartida());
            //Si le pertenece entonces hago cambio de saldo
            if(pup!=null){
                Boolean resultado = transferirDineroAlDestinatario(usuarioAMover, pup.getPartidaUsuario(), pup.getPropiedad());
                if(!resultado)
                    throw new UsuarioPerdedorException();

                //INDICO CON UN MENSAJE EL USUARIO A PAGAR.
                DatosPagarPropiedad pagarMensaje = new DatosPagarPropiedad();
                pagarMensaje.setDescripcionQuienPaga("EL JUGADOR "+usuarioAMover.getUsuario().getNombreUsuario()+ "CAYO EN UNA PROPIEDAD!!");
                pagarMensaje.setDescripcionQuienRecibe(usuarioAMover.getUsuario().getNombreUsuario() + " PAGA AL PROPIETARIO $"+pup.getPartidaUsuario().getUsuario().getNombreUsuario());
                session.setAttribute("pagarMensaje", pagarMensaje);
            }
        }

        //Establezco su posicion
        usuarioAMover.setPosicionCasilla(posicionObtenida);
        this.repositorioPartidaUsuario.actualizarPartidaUsuario(usuarioAMover);
    }

    private Propiedad determinarSiPisoEnAlgunaPropiedadDisponible(Integer posicionDelUsuario,Partida partidaEnJuego){
        //Obtengo la propiedad
        Propiedad propiedadEnLaCasilla  = this.repositorioPropiedad.obtenerPropiedadPorNroCasillero(posicionDelUsuario);

        //Obtengo las propiedades de todos los jugadores dentro de una partida
        List<PartidaUsuario> usuariosEnLaPartida = this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(partidaEnJuego.getId());
        List<PartidaUsuarioPropiedad> partidaUsuarioPropiedades = new ArrayList<PartidaUsuarioPropiedad>();
        usuariosEnLaPartida.forEach(up -> partidaUsuarioPropiedades.addAll(up.getPropiedades()));

        List<Propiedad> propiedadesNoDisponibles  = partidaUsuarioPropiedades
                .stream()
                .map(pup->pup.getPropiedad())
                .collect(Collectors.toList());

        //Verifico si esta disponible
        if(propiedadesNoDisponibles.contains(propiedadEnLaCasilla))
            return null;

        return propiedadEnLaCasilla;
    }

    private PartidaUsuarioPropiedad verSiLaPropiedadLePerteneceAAlguien(Integer posicionDelUsuario,Usuario usuarioQuienPaga, Partida partidaEnJuego){
        Propiedad propiedadEnLaCasilla  = this.repositorioPropiedad.obtenerPropiedadPorNroCasillero(posicionDelUsuario);

        if(propiedadEnLaCasilla == null)
            return null;

        List<PartidaUsuario> usuariosEnLaPartida = this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(partidaEnJuego.getId());
        List<PartidaUsuarioPropiedad> partidaUsuarioPropiedades = new ArrayList<PartidaUsuarioPropiedad>();
        usuariosEnLaPartida.forEach(up -> partidaUsuarioPropiedades.addAll(up.getPropiedades()));

        PartidaUsuarioPropiedad propietario = partidaUsuarioPropiedades.stream()
                .filter(pup-> pup.getPropiedad().equals(propiedadEnLaCasilla))
                .findAny().get();

        if(propietario.getPartidaUsuario().getUsuario().equals(usuarioQuienPaga))
            return null;

        return propietario;
    }

    private Boolean transferirDineroAlDestinatario(PartidaUsuario usuarioQuienPaga, PartidaUsuario usuarioQuienRecibe, Propiedad propiedadACobrar){
        if(usuarioQuienPaga.getSaldo() < propiedadACobrar.getPrecio())
            return false; //pierde

        //Aca el usuarioQuienPaga establece su nuevo saldo restante
        usuarioQuienPaga.setSaldo(usuarioQuienPaga.getSaldo() - propiedadACobrar.getPrecio());

        //Aca el usuarioQuienRecibe establece su nuevo saldo
        usuarioQuienRecibe.setSaldo(usuarioQuienRecibe.getSaldo() + propiedadACobrar.getPrecio());

        //Actualizo ambas entidades
        this.repositorioPartidaUsuario.actualizarPartidaUsuario(usuarioQuienPaga);
        this.repositorioPartidaUsuario.actualizarPartidaUsuario(usuarioQuienRecibe);
        return true; //indico que pago
    }

    @Override
    public void hacerCambioTurno(PartidaUsuario partidaUsuario, Partida partidaEnJuego) {
        /*obtengo todos los usuarios jugando a la misma partida*/
        List<PartidaUsuario> usuarioJugandoALaMismaPartida = this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(partidaEnJuego.getId());

        /*Hago el cambio de turno*/
        int ordenTurnoActual = usuarioJugandoALaMismaPartida.indexOf(partidaUsuario);
        int siguienteTurno = (ordenTurnoActual + 1) % usuarioJugandoALaMismaPartida.size();

        PartidaUsuario proximoUsuarioATirar = usuarioJugandoALaMismaPartida.get(siguienteTurno);
        partidaEnJuego.setTurnoJugador(proximoUsuarioATirar.getUsuario());

        /*Guardo cambios*/
        this.repositorioPartida.actualizarPartida(partidaEnJuego);
    }

    @Override
    public void adquirirPropiedad(Long propiedadId, PartidaUsuario usuarioQuienCompra) throws SaldoInsuficienteException {
        Propiedad propiedad = this.repositorioPropiedad.obtenerPropiedadPorId(propiedadId);

        if(usuarioQuienCompra.getSaldo() < propiedad.getPrecio())
            throw new SaldoInsuficienteException();


        PartidaUsuarioPropiedad usuarioYSuPropiedadNueva = new PartidaUsuarioPropiedad();
        usuarioYSuPropiedadNueva.setPartidaUsuario(usuarioQuienCompra);
        usuarioYSuPropiedadNueva.setPropiedad(propiedad);
        this.repositorioPartidaUsuarioPropiedad.crearPartidaUsuarioPropiedad(usuarioYSuPropiedadNueva);

        usuarioQuienCompra.setSaldo(usuarioQuienCompra.getSaldo() - propiedad.getPrecio());
        this.repositorioPartidaUsuario.actualizarPartidaUsuario(usuarioQuienCompra);
    }

    @Override
    public List<PartidaUsuario> obtenerTodosLosUsuariosJugandoEnLaPartidaId(Long partidaId) {
        return this.repositorioPartidaUsuario.obtenerPartidasUsuariosEnlaPartidaId(partidaId);
    }

    @Override
    public List<DatosPropiedadUsuario>tenerDatosDeLasPropiedadesDeLosUsuarios(List<PartidaUsuario> usuariosJugando){
        List<DatosPropiedadUsuario> datosPropiedadUsuarios = new ArrayList<>();
        for (PartidaUsuario usuario : usuariosJugando){
            usuario.getPropiedades().forEach(
                    p -> datosPropiedadUsuarios.add(new DatosPropiedadUsuario(p.getPropiedad(),usuario.getUsuario(),usuario.getColorUsuario().getCodigoHexadecimal())));
        }

        return datosPropiedadUsuarios;
    }

    @Override
    public PartidaUsuario obtenerUsuarioPartidaPorPartidaIdYUsuarioId(Long partidaId, Long usuarioId) {
        return this.repositorioPartidaUsuario.obtenerUsuarioPartidaPorPartidaIdYUsuarioId(partidaId,usuarioId);
    }

    @Override
    public Partida obtenerPartidaPorPartidaId(Long partidaId) {
        return this.repositorioPartida.obtenerPartidaPorId(partidaId);
    }


}
