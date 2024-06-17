const usuarioNombre = document.currentScript.getAttribute("usuario-nombre");
const partidaIdActual = document.currentScript.getAttribute("partida-id");
const usuarioIdActual = document.currentScript.getAttribute("usuario-id");
const usuarioPartidaId = document.currentScript.getAttribute("usuario-partida-id");

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/spring/wsmonopolychat'
});
stompClient.debug = function(str) {
    console.log(str)
};

const chatMensajesElem = document.querySelector('.chat__mensajes');
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    //Estado de escucha del chat
    stompClient.subscribe('/topic/recibirMensajeChat', (m) => {
        chatMensajesElem.innerHTML += `<p>${JSON.parse(m.body).message}</p>`;
    });

    stompClient.subscribe('/topic/recibirActualizacionDeTablero',(m)=>{
        //location.href="http://localhost:8080/spring/monopoly/?id="+partidaIdActual;
        window.removeEventListener("beforeunload", establecerInactivoAlJugadorActual);
        location.reload();
    });

    stompClient.subscribe("/topic/recibirCierreSocket", (m) =>{
        console.log(JSON.parse(m.body));
        //Evito que los demas pasen por el mismo evento de salir partida
        window.removeEventListener("beforeunload", establecerInactivoAlJugadorActual);
    //  location.href="http://localhost:8080/spring/monopoly/?id="+partidaIdActual;
        location.reload();
    });

};

stompClient.onWebSocketError = (error) => {
    console.error('Error with websocket', error);
};

stompClient.onStompError = (frame) => {
    console.error('Broker reported error: ' + frame.headers['message']);
    console.error('Additional details: ' + frame.body);
};

stompClient.activate();

//Actualizo el estado de activo a inactivo de un jugador
function establecerInactivoAlJugadorActual() {
    navigator.sendBeacon("http://localhost:8080/spring/api/partida/establecerEstado/?idPartidaUsuario=" + usuarioPartidaId + "&estado=inactivo");
    stompClient.publish({
        destination: "/app/indicarCierreSocket",
        body: JSON.stringify({
            message: "Ripeo un socket"
        })
    });
}
window.addEventListener("beforeunload",establecerInactivoAlJugadorActual);

//Establezco la reconexion de un inactivo
function establecerActivoAUnJugadorActual(){
    navigator.sendBeacon("http://localhost:8080/spring/api/partida/establecerEstado/?idPartidaUsuario=" + usuarioPartidaId + "&estado=activo");
    setTimeout(()=>{
        stompClient.publish({
            destination: "/app/enviarActualizacionDeTablero",
            body: JSON.stringify({
                message: "Reconexion establecida"
            })
        })
    },2000);
}

document.querySelector(".boton__reconectarse").addEventListener("click",establecerActivoAUnJugadorActual);


/*Enviar Mensaje por el chat*/
function enviarMensaje(message){
    stompClient.publish({
        destination: "/app/enviarMensajeChat",
        body: JSON.stringify({
            message: message
        })
    });
}

/*Notificar movimiento*/
function enviarNotificacionMovimiento(message){
    stompClient.publish(
        {
            destination: "/app/enviarActualizacionDeTablero",
            body: JSON.stringify({
                message: message
            })
        }
    )
}

function eliminarJugadorPorBancarrota(){
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/spring/api/partida/eliminar/?partidaId="+partidaIdActual+"&usuarioId="+usuarioIdActual,
        dataType:"json",
        success: function(){
            enviarNotificacionMovimiento();
        }
    });
}

/*Funcion que capta eventos en Javascript*/
document.addEventListener("DOMContentLoaded", ()=>{
    document.addEventListener("click", (e)=>{
        /*Cuando presione en partida boton*/
        const inputElem = document.querySelector("#inputMensaje");
        if(e.target.matches("#enviarMensaje") || e.target.matches("#enviarMensaje *")){
            enviarMensaje(usuarioNombre+":"+inputElem.value);
            inputElem.value="";
        }

        if(e.target.matches(".aceptar_boton") || e.target.matches(".aceptar_boton *")){
            enviarNotificacionMovimiento("Movimiento hecho");
        }

        if(e.target.matches(".volver_boton" || e.target.matches("volver_boton *")))
            eliminarJugadorPorBancarrota();
    })
});



