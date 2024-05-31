let flagNotificacion = localStorage.getItem('flagNotificacion') === 'true';

const partidaIdActual = document.currentScript.getAttribute("partida-actual-id");

const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/spring/wsespera'
});

stompClient.debug = function(str) {
    console.log(str)
};

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);

    //Mando una señal de que me conecte: (Se envia 1 vez por conexion)
    if (!flagNotificacion) {

        stompClient.publish({
            destination: "/app/enviarNotificacionSalaEspera",
            body: JSON.stringify({
                message: "Usuario nuevo en la sala de espera"
            })
        });

        flagNotificacion = true; // Marca la notificación como enviada
        localStorage.setItem('flagNotificacion', 'true');
    }

    //Estado de escucha
    stompClient.subscribe('/topic/recibirNotificacionSalaEspera', (m) => {
        location.href = "http://localhost:8080/spring/espera/?id=" + partidaIdActual;
    });

    stompClient.subscribe('/topic/recibirEmpezoJuego', (m) => {
        console.log("Recibiendo ir al monopoly.....");
        location.href = "http://localhost:8080/spring/monopoly";
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


function comenzarPartidaDelMonopoly(){
    console.log("Mandando ir al monopoly.....");
    stompClient.publish({
        destination: "/app/enviarEmpezoJuego",
        body: JSON.stringify({
            message: "El juego ya empezo"
        })
    });
}

document.addEventListener("click", (e)=>{
    console.log(e.target);
    if(e.target.matches("#boton_iniciar_partida") || e.target.matches("#boton_iniciar_partida *")){
        comenzarPartidaDelMonopoly();
    }
});
