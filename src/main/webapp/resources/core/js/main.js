import {abrirChat,cerrarChat} from "./chat.js";
import {cerrarVentanaEmergente} from "./cerrar_ventana.js";

document.addEventListener("click",(e)=>{
    console.log(e);
    //Evento si abre ventana
   if(e.target.matches(".boton__chat") || e.target.matches(".boton__chat *"))
       abrirChat(".chat__ventana");
   //Evento si cierra ventana
   if(e.target.matches(".boton__chat__cerrar") || e.target.matches(".boton__chat__cerrar *"))
       cerrarChat(".chat__ventana");

   if(e.target.matches("#aceptar_dado") || e.target.matches("#aceptar_dado *"))
        cerrarVentanaEmergente(".dado__ventana");

   if(e.target.matches(".cancelar_propiedad_boton") || e.target.matches(".cancelar_propiedad_boton *"))
       cerrarVentanaEmergente(".comprar_propiedad_servicio");
});
