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

    //Mando una señal de que me conecte: (1 vez se envia por conexion)
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
        if (window.location.pathname !== '/spring/espera/?id='+partidaIdActual) {
            location.href = "http://localhost:8080/spring/espera/?id=" + partidaIdActual;
        }
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


