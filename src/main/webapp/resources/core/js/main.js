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

    if(e.target.matches(".boton__musica") || e.target.matches(".boton__musica *")){
        let $iconAudio = document.querySelector(".boton__musica i");
        console.log($iconAudio);
        if($iconAudio.classList.contains("fa-music-note-slash"))
            reproducir($iconAudio,audioMusica);
        else
            pausar($iconAudio,audioMusica);

    }
});
