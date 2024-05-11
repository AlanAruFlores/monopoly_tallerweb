import {abrirChat,cerrarChat} from "./chat.js";
document.addEventListener("click",(e)=>{
    console.log(e);
    //Evento si abre ventana
   if(e.target.matches(".boton__chat") || e.target.matches(".boton__chat *"))
       abrirChat(".chat__ventana");
   //Evento si cierra ventana
   if(e.target.matches(".boton__chat__cerrar") || e.target.matches(".boton__chat__cerrar *"))
       cerrarChat(".chat__ventana");

});
