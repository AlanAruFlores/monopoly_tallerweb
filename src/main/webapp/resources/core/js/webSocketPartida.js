const stompClient = new StompJs.Client({
    brokerURL: 'ws://localhost:8080/spring/wspartida'
});

stompClient.debug = function(str) {
    console.log(str)
};

stompClient.onConnect = (frame) => {
    console.log('Connected: ' + frame);
    //Estado de escucha
    stompClient.subscribe('/topic/notificacionPartida', (m) => {
        console.log("Hola");
        $.ajax({
            type: "GET",
            url: "http://localhost:8080/spring/api/partida/obtenerTodasPartidas",
            dataType:"json",
            success: function(partidas){
                const $tbody = document.querySelector("#tablaPartidas tbody");
                let $filas = "";
                $.each(partidas, function(i, partida){
                    $filas +=  `
                    <tr>
                        <td>${partida.nombre}</td>
                        <td>${partida.numeroJugadores}</td>
                        <td>${partida.creador.nombre}</td>
                        <td>${partida.estadoPartida}</td>
                        <td>
                            <a href="http://localhost:8080/spring/unirsePartida/?id=${partida.id}" th:href="@{/unirsePartida/(id=${partida.id})}" class="partida__boton">
                                <i class="fa-thin fa-arrow-right-to-bracket"></i>
                            </a>
                        </td>
                    </tr>
                `;
                })
                $tbody.innerHTML = $filas;
            },
        });
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



function notificarALosJugadoresDeNuevaPartida(message){
    stompClient.publish({
        destination: "/app/partidaNueva",
        body: JSON.stringify({
            message: message
        })
    });
}

function irAlAPartidaYaCreada(){
    console.log("Yendo sa la partida");
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/spring/api/partida/obtenerPartidaDelCreador",
        dataType:"json",
        success: function(partidaRecienCreada){
            console.log(partidaRecienCreada);
            location.href= "http://localhost:8080/spring/unirsePartida/?id="+partidaRecienCreada.id;
        }
    });
}

function ejecutarCallBacks(funcion,tiempo){
    setTimeout(()=>funcion(),tiempo);
}

/*Funcion que capta eventos en Javascript*/
document.getElementById("formularioCrearPartida").addEventListener("submit", function(e) {
    e.preventDefault();

    ejecutarCallBacks(()=>{
        $.ajax({
            type: "POST",
            url: "http://localhost:8080/spring/api/partida/crearPartida",
            contentType: "application/json", // Especifica el tipo de medio como JSON
            data: JSON.stringify({
                nombre: document.querySelector("#nombreInput").value,
                numero: document.querySelector("#numeroInput").value
            }), // Convierte el objeto a JSON
            dataType: "json",
            success: function(){
                console.log("POST HECHOqwdqwdwqdqwd");
            }
        });
        ejecutarCallBacks(()=>
        {
            notificarALosJugadoresDeNuevaPartida("Partida Nueva!!")
            ejecutarCallBacks(()=>irAlAPartidaYaCreada(),500)
        }, 0)
    }, 0)

    const $ventana = document.querySelector("#crear__partida__ventana");
        $ventana.style.setProperty("visibility","hidden");
        $ventana.style.setProperty("opacity", "0");
});




