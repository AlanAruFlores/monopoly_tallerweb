import {cerrarVentana,abrirVentana} from "./cerrar_abrir_ventana.js";
import {reproducir,pausar} from "./reproducir_musica.js";


const audioMusica = new Audio("/spring/audio/monopoly_audio.mp3");
audioMusica.loop=true;

document.addEventListener("DOMContentLoaded",()=>{
//Eventos de click
    document.addEventListener("click",(e)=>{
        /*EVENTOS PARA EL MONOPOLY*/
        //Evento si abre ventana
        if(e.target.matches(".boton__chat") || e.target.matches(".boton__chat *"))
            abrirVentana(".chat__ventana");
        //Evento si cierra ventana
        if(e.target.matches(".boton__chat__cerrar") || e.target.matches(".boton__chat__cerrar *"))
            cerrarVentana(".chat__ventana");

        if(e.target.matches(".aceptar_dado") || e.target.matches(".aceptar_dado *"))
            cerrarVentana(".ventana__emergente");

        if(e.target.matches(".cancelar_propiedad_boton") || e.target.matches(".cancelar_propiedad_boton *"))
            cerrarVentana(".comprar_propiedad_servicio");

        /*Abrimos y cerramos la ventana de propiedades*/
        if(e.target.matches(".boton__propiedades") || e.target.matches(".boton__propiedades *"))
            abrirVentana(".ventana__propiedades")

        if(e.target.matches(".boton__propiedades__cerrar") || e.target.matches(".boton__propiedades__cerrar *"))
            cerrarVentana(".ventana__propiedades");


        /*EVENTOS PARA LA VISTA PARTIDA*/
        if(e.target.matches("#crear__partida__boton") || e.target.matches("#crear__partida__boton *"))
            abrirVentana("#crear__partida__ventana");
        if(e.target.matches("#cerrar__partida__ventana") || e.target.matches("#cerrar__partida__ventana *"))
            cerrarVentana("#crear__partida__ventana");

        /*Cerramos la ventana emergente con el mensaje especifico*/
        if(e.target.matches(".mensaje_boton") || e.target.matches(".mensaje_boton *"))
            cerrarVentana(".ventana__emergente");

        /*Para musica*/
        if(e.target.matches(".boton__musica") || e.target.matches(".boton__musica *")){
            let $iconAudio = document.querySelector(".boton__musica i");
            console.log($iconAudio);
            if($iconAudio.classList.contains("fa-music-note-slash"))
                reproducir($iconAudio,audioMusica);
            else
                pausar($iconAudio,audioMusica);
        }
        /*Para intercambiar*/

        if(e.target.matches("#intercambio__boton") || e.target.matches("#intercambio__boton *"))
            abrirVentana(".ventana__intercambio");
        if(e.target.matches(".boton__intercambio__cerrar") || e.target.matches(".boton__intercambio__cerrar *"))
            cerrarVentana(".ventana__intercambio");
        if(e.target.matches(".boton__hacerintercambio__cerrar") || e.target.matches(".boton__hacerintercambio__cerrar *"))
            cerrarVentana(".ventana__intercambio__propiedades")
    });

//Efecto para mostrar el cartel "TU TURNO"
    /*
    setTimeout(()=>{
        cerrarVentana("#ventana__tuturno")
        console.log("Hola");
    },2500);*/
})
