package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.ServicioMonopoly;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

@Service("servicioMonopoly")
@Transactional
public class ServiceMonopolyImpl implements ServicioMonopoly {


    @Override
    public void obtenerPosicionCasillero(HttpSession session) {
        Integer numeroRandom = (int) (1 + (Math.random() * (6 - 1)));
        session.setAttribute("dado",numeroRandom);
        Integer numeroSesion;
        numeroSesion = (Integer) session.getAttribute("numeroRandom");
        numeroRandom += numeroSesion;
        if(numeroRandom > 20){
            numeroRandom = (numeroRandom-20) ;
        }
        session.setAttribute("numeroRandom", numeroRandom);
    }
}
