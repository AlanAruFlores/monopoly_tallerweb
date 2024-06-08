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
    stompClient.publish({
        destination: "/app/enviarNotificacionSalaEspera",
        body: JSON.stringify({
            message: "Usuario nuevo en la sala de espera"
        })
    });

    //Estado de escucha
    stompClient.subscribe('/topic/recibirNotificacionSalaEspera', (m) => {
        $.ajax({
            type:"GET",
            url:"http://localhost:8080/spring/api/usuario/obtenerUsuarioEnLaPartida/?id="+partidaIdActual,
            dataType:"json",
            success: function(usuarios){
                const $salaJugadores = document.querySelector("#sala__jugadores");
                let $usuario ="";
                $.each(usuarios, function(i, usuarioItem){
                    console.log(usuarioItem);
                    $usuario += `<div class="sala__jugador">
                      <i class='fa-solid fa-user'></i>
                      <h4>${usuarioItem.nombreUsuario}</h4>
                    </div>`;
                });
                $salaJugadores.innerHTML=$usuario;
            }
        })
    });

    stompClient.subscribe('/topic/recibirEmpezoJuego', (m) => {
        console.log("Recibiendo ir al monopoly.....");
        location.href = "http://localhost:8080/spring/monopoly/?id="+partidaIdActual;
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
