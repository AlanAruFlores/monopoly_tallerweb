package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioMonopoly;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Service("servicioMonopoly")
@Transactional
public class ServiceMonopolyImpl implements ServicioMonopoly {

    /*Enlace de Dados y Su numero*/
    Map<Integer, String> mapaDado = new HashMap<Integer, String>();

    public ServiceMonopolyImpl() {
        mapaDado.put(1,"/imagenes/dados/dado1.png");
        mapaDado.put(2,"/imagenes/dados/dado2.png");
        mapaDado.put(3,"/imagenes/dados/dado3.png");
        mapaDado.put(4,"/imagenes/dados/dado4.png");
        mapaDado.put(5,"/imagenes/dados/dado5.png");
        mapaDado.put(6,"/imagenes/dados/dado6.png");
    }

    @Override
    public void obtenerPosicionCasillero(HttpSession session) {
        Integer numeroRandom = (int) (1 + (Math.random() * (6 - 1)));
        session.setAttribute("dado",mapaDado.get(numeroRandom));

        /*Actualizo posicion*/
        Integer numeroSesion;
        numeroSesion = (Integer) session.getAttribute("numeroRandom");
        numeroRandom += numeroSesion;
        if(numeroRandom > 20){
            numeroRandom = (numeroRandom-20) ;
        }
        session.setAttribute("numeroRandom", numeroRandom);
    }
}
