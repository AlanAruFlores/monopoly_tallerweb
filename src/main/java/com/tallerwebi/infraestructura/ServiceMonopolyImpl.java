package com.tallerwebi.infraestructura;

import com.tallerwebi.dominio.Propiedad;
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

    /*Mapa de Propiedades/Servicios*/
    Map<Integer, Propiedad> mapaPropiedades = new HashMap<Integer,Propiedad>();

    public ServiceMonopolyImpl() {
        /*Llenamos datos al mapa */
        mapaDado.put(1,"/imagenes/dados/dado1.png");
        mapaDado.put(2,"/imagenes/dados/dado2.png");
        mapaDado.put(3,"/imagenes/dados/dado3.png");
        mapaDado.put(4,"/imagenes/dados/dado4.png");
        mapaDado.put(5,"/imagenes/dados/dado5.png");
        mapaDado.put(6,"/imagenes/dados/dado6.png");

        /*Llenamos datos al mapa de propiedades*/
        mapaPropiedades.put(2, new Propiedad(2, 100,"/imagenes/propiedades/celeste/balcarce.png", "Calle Balcarce"));
        mapaPropiedades.put(3, new Propiedad(3, 100,"/imagenes/propiedades/celeste/belgrano.png", "Avenida Belgrano"));
        mapaPropiedades.put(4, new Propiedad(4, 500,"/imagenes/propiedades/trenes/roca.png","Tren Roca"));
        mapaPropiedades.put(5, new Propiedad(5, 100,"/imagenes/propiedades/celeste/juramento.png", "Calle Juramento"));
        mapaPropiedades.put(7, new Propiedad(7, 200,"/imagenes/propiedades/rosa/9julio.png", "Avenida 9 de Julio"));
        mapaPropiedades.put(8, new Propiedad(8, 200,"/imagenes/propiedades/rosa/arribeños.png", "Calle Aribeño"));
        mapaPropiedades.put(9, new Propiedad(9, 250,"/imagenes/propiedades/utilidades/edenor.png", "Edenor"));
        mapaPropiedades.put(10, new Propiedad(10, 200,"/imagenes/propiedades/rosa/mendoza.png", "Calle Mendoza"));

        mapaPropiedades.put(12, new Propiedad(12, 400,"/imagenes/propiedades/roja/mayo.png", "Avenida de Mayo"));
        mapaPropiedades.put(13, new Propiedad(13, 400,"/imagenes/propiedades/roja/rivadavia.png", "Avenida Rivadavia"));
        mapaPropiedades.put(14, new Propiedad(14, 500,"/imagenes/propiedades/trenes/sarmiento.png", "Tren Sarmiento"));
        mapaPropiedades.put(15, new Propiedad(15, 400,"/imagenes/propiedades/roja/varela.png", "Florencio Varela"));
        mapaPropiedades.put(17, new Propiedad(17, 600,"/imagenes/propiedades/verde/corrientes.png", "Avenida Corrientes"));
        mapaPropiedades.put(18, new Propiedad(18, 600,"/imagenes/propiedades/verde/florida.png", "Avenida Florida"));
        mapaPropiedades.put(19, new Propiedad(19, 350,"/imagenes/propiedades/utilidades/aysa.png", "Aysa"));
        mapaPropiedades.put(20, new Propiedad(20, 400,"/imagenes/propiedades/verde/santafe.png", "Avenida Santa Fe"));
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
        //Agrego propiedad
        session.setAttribute("propiedad", mapaPropiedades.get(numeroRandom));
    }
}
