console.log(document.currentScript.getAttribute("usuario-nombre"));
const usuarioNombre = document.currentScript.getAttribute("usuario-nombre");

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/spring/wsmonopolychat'
});
stompClient.debug = function(str) {
    console.log(str)
};


const chatMensajesElem = document.querySelector('.chat__mensajes');
stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    //Estado de escucha
    stompClient.subscribe('/topic/recibirMensajeChat', (m) => {
        chatMensajesElem.innerHTML += `<p>${JSON.parse(m.body).message}</p>`;
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



function enviarMensaje(message){
    stompClient.publish({
        destination: "/app/enviarMensajeChat",
        body: JSON.stringify({
            message: message
        })
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
    })
});



