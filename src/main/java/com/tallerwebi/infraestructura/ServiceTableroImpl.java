package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioTablero;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service("servicioTablero")
@Transactional
public class ServiceTableroImpl implements ServicioTablero {

    @Override
    public void obtenerRandom(HttpSession session) {
        Integer numeroRandom = (int) (1+(Math.random()*(6-1)));

        Integer numeroSesion;
        if(session.getAttribute("numeroRandom") != null)
            numeroSesion = (Integer) session.getAttribute("numeroRandom");
        else
            numeroSesion = 0;

        numeroRandom += numeroSesion;

        if(numeroRandom > 16){
            numeroRandom = (numeroRandom-16) ;
        }
        session.setAttribute("numeroRandom", numeroRandom);
    }
}
