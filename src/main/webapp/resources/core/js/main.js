import {cerrarVentana,abrirVentana} from "./cerrar_abrir_ventana.js";
import {reproducir,pausar} from "./reproducir_musica.js";


const audioMusica = new Audio("/spring/audio/monopoly_audio.mp3");
audioMusica.loop=true;

document.addEventListener("click",(e)=>{
    // console.log(e);
    //Evento si abre ventana
    if(e.target.matches(".boton__chat") || e.target.matches(".boton__chat *"))
        abrirVentana(".chat__ventana");
    //Evento si cierra ventana
    if(e.target.matches(".boton__chat__cerrar") || e.target.matches(".boton__chat__cerrar *"))
        cerrarVentana(".chat__ventana");

    if(e.target.matches("#aceptar_dado") || e.target.matches("#aceptar_dado *"))
        cerrarVentana(".dado__ventana");

    if(e.target.matches(".cancelar_propiedad_boton") || e.target.matches(".cancelar_propiedad_boton *"))
        cerrarVentana(".comprar_propiedad_servicio");

    /*Cerramos la ventana emergente con el mensaje especifico*/
    if(e.target.matches("#mensaje_boton") || e.target.matches("#mensaje_boton *"))
        cerrarVentana(".ventana__mensaje");

    /*Abrimos y cerramos la ventana de propiedades*/
    if(e.target.matches(".boton__propiedades") || e.target.matches(".boton__propiedades *"))
        abrirVentana(".ventana__propiedades")

    if(e.target.matches(".boton__propiedades__cerrar") || e.target.matches(".boton__propiedades__cerrar *"))
        cerrarVentana(".ventana__propiedades");


    /*Abrir boton para crear partida*/
    if(e.target.matches("#crear__partida__boton") || e.target.matches("#crear__partida__boton *"))
        abrirVentana("#crear__partida__ventana");

    if(e.target.matches("#cerrar__partida__ventana") || e.target.matches("#cerrar__partida__ventana *"))
        cerrarVentana("#crear__partida__ventana");

    if(e.target.matches(".boton__musica") || e.target.matches(".boton__musica *")){
        let $iconAudio = document.querySelector(".boton__musica i");
        console.log($iconAudio);
        if($iconAudio.classList.contains("fa-music-note-slash"))
            reproducir($iconAudio,audioMusica);
        else
            pausar($iconAudio,audioMusica);

    }
});
