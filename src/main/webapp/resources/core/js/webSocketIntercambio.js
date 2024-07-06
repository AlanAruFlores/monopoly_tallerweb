
/*CLICK EN EL BOTON DE INTERCAMBIAR DE LA VENTANADE USUARIOS*/
document.querySelectorAll(".boton__intercambiar").forEach(boton =>{
    boton.addEventListener("click",(e)=>{
        console.log(e.target.children[0]);
        let idPartidaUsuario = e.target.children[0].value;
        $.ajax({
            type:"GET",
            url:"http://localhost:8080/spring/api/partidausuario/obtenerPorId/?id="+idPartidaUsuario,
            dataType:"json",
            success: function (partidaUsuarioJson){
                console.log(partidaUsuarioJson);
                cerrarVentana(".ventana__intercambio");
                abrirVentana(".ventana__intercambio__propiedades");

                let partidaUsuarioListaHtml = "";
                for (const propiedadUsuario of partidaUsuarioJson.propiedades) {
                    partidaUsuarioListaHtml += `
                    <div data-player="jugador2" class="house-div">
                      <input class="idPropiedad" type="hidden" value="${propiedadUsuario.propiedad.id}">
                      <div class="franja" style="background-color:${propiedadUsuario.propiedad.color}"></div>
                      <div>
                        <p class="house-parrafo">${propiedadUsuario.propiedad.nombre} ${propiedadUsuario.propiedad.precio}"</p>
                      </div>
                    </div>`
                }
                document.querySelector("#jugador2Columna").innerHTML=`
                <div class="player-info">
                  <div class="profile">
                    <input type="hidden" value="${partidaUsuarioJson.id}" id="receptorId">
                    <img th:src="@{${partidaUsuarioJson.usuario.imagenPerfil}}" alt="Perfil Jugador 2" class="profile-img">
                    <span class="nombre">${partidaUsuarioJson.usuario.nombreUsuario}</span>
                    <div class="money">
                      <input type="number" id="saldoReceptor" pattern="[0-9]{4}" max="${partidaUsuarioJson.saldo}" placeholder="$">
                    </div>
                  </div>
                  <div class="selected-houses">
                    <div class="house-slot" data-player="jugador2" data-slot="1"></div>
                    <input class="propiedadId" id="idPropiedadReceptorUno" type="hidden" value="">
                    <div class="house-slot" data-player="jugador2" data-slot="2"></div>
                    <input class="propiedadId" id="idPropiedadReceptorDos" type="hidden" value="">
                  </div>
                  <div class="house-options">
                      ${partidaUsuarioListaHtml}             
                   </div>
                </div>
                `;

                cargarEventos();
            }
        })

    })
})


function cargarEventos(){
    /*OTROS*/
    document.querySelectorAll('.house-div').forEach(div => {
        div.addEventListener('click', function() {
            selectHouse(this.dataset.player, div);
        });
    });
}


function selectHouse(player, div) {
    const slots = document.querySelectorAll(`.house-slot[data-player="${player}"]`);
    const emptySlot = Array.from(slots).find(slot => !slot.innerHTML);
    console.log(emptySlot.nextElementSibling);
    console.log(div);
    console.log(div.querySelector(".idPropiedad"));

    if (emptySlot) {
        console.log(emptySlot.nextElementSibling);
        emptySlot.nextElementSibling.setAttribute("value",div.querySelector(".idPropiedad").value);

        emptySlot.innerHTML += div.innerHTML;
        div.style.display = "none";
        emptySlot.style.backgroundColor = "#fff";
    }
}

document.getElementById('exchange-button').addEventListener('click', function() {
    exchange();
});

document.getElementById('cancel-button').addEventListener('click', function() {
    cancel();
});

function exchange() {
    /*
    alert('Intercambio realizado');
    cancel(); // Opcional: limpia los slots después del intercambio*/
    $.ajax({
       type:"POST",
       url:"http://localhost:8080/spring/api/intercambio/hacerIntercambio",
       contentType:"application/json",
       data: JSON.stringify({
           emisorId: document.querySelector("#emisorId").value,
           receptorId: document.querySelector("#receptorId").value,
           saldoEmisor: document.querySelector("#saldoEmisor").value === "" ? 0 : document.querySelector("#saldoEmisor").value,
           saldoReceptor: document.querySelector("#saldoReceptor").value === "" ? 0: document.querySelector("#saldoReceptor").value,
           idPropiedadEmisorUno: (document.querySelector("#idPropiedadEmisorUno").value === "") ? null : document.querySelector("#idPropiedadEmisorUno").value,
           idPropiedadEmisorDos: (document.querySelector("#idPropiedadEmisorDos").value === "") ? null : document.querySelector("#idPropiedadEmisorDos").value,
           idPropiedadReceptorUno: (document.querySelector("#idPropiedadReceptorUno").value === "") ? null : document.querySelector("#idPropiedadReceptorUno").value,
           idPropiedadReceptorDos: (document.querySelector("#idPropiedadReceptorDos").value === "") ? null : document.querySelector("#idPropiedadReceptorDos").value
       }),
       datatype:"json",
        success: function(){
            realizarIntercambio();
        }

    });
}

function realizarIntercambio(){
    console.log("POST INTERCAMBIO HECHO");
    stompClient.publish({
        destination: "/app/notificarIntercambio",
        body: JSON.stringify({
            message: "Intercambio enviado"
        })
    });
    window.removeEventListener("beforeunload", establecerInactivoAlJugadorActual);
    window.location.reload();
}


function cancel() {
    const slots = document.querySelectorAll('.house-slot');

    slots.forEach(slot => {
        slot.innerHTML = ''
        slot.style.backgroundColor = "transparent";
    });

    document.querySelectorAll(".propiedadId").forEach(propiedadIds=>{
        propiedadIds.value="";
    });

    document.querySelectorAll('.house-div').forEach(div => {
        div.style.display="flex";
    });
}


const idIntercambio = document.currentScript.getAttribute("intercambio-id");
function aceptarIntercambio(){
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/spring/api/intercambio/cambiarEstadoAceptado/?id="+idIntercambio,
        success: function(){
            notificarIntercambioAceptado();

        }
    });

    setTimeout(()=>{
    },1500);
}

function notificarIntercambioAceptado(){
    stompClient.publish({
        destination: "/app/notificarIntercambio",
        body: JSON.stringify({
            message: "Intercambio enviado"
        })
    });

    window.removeEventListener("beforeunload", establecerInactivoAlJugadorActual);
    window.location.reload();
}

function notificarIntercambioRechazado(){
    stompClient.publish({
        destination: "/app/notificarIntercambio",
        body: JSON.stringify({
            message: "Intercambio rechazado"
        })
    });

    window.removeEventListener("beforeunload", establecerInactivoAlJugadorActual);
    window.location.reload();
}

function rechazarIntercambio(){
    $.ajax({
        type:"GET",
        url:"http://localhost:8080/spring/api/intercambio/cambiarEstadoRechazado/?id="+idIntercambio,
        success: function(){
            notificarIntercambioRechazado();
        }
    });
/*
    setTimeout(()=>{
        notificarIntercambioRechazado();
    },1500);*/
}

function continuar(){
    $.ajax({
       type:"GET",
       url:"http://localhost:8080/spring/api/intercambio/eliminarIntercambio/?id="+idIntercambio,
       success:function (){
           stompClient.publish({
               destination: "/app/notificarIntercambio",
               body: JSON.stringify({
                   message: "Intercambio rechazado"
               })
           });
           window.removeEventListener("beforeunload", establecerInactivoAlJugadorActual);
           window.location.reload();
       }
    });
}

document.addEventListener("click", (e)=>{
    console.log("EVENTOS: "+e.target);

    if(e.target.matches("#aceptar_intercambio") || e.target.matches("#aceptar_intercambio *")) {
        console.log("Hola");
        aceptarIntercambio();
    }

    if(e.target.matches("#rechazar_intercambio") || e.target.matches("#rechazar_intercambio *")) {
        console.log("Hola2");
        rechazarIntercambio()
    }

    if(e.target.matches(".ventana__continuar") || e.target.matches(".ventana__continuar *")){
        continuar();
    }
});


function cerrarVentana(ventanaEmergente){
    const $ventana = document.querySelector(ventanaEmergente);
    $ventana.style.setProperty("visibility","hidden");
    $ventana.style.setProperty("opacity", "0");
}
function abrirVentana(ventanaEmergente){
    const $ventana = document.querySelector(ventanaEmergente);
    $ventana.style.setProperty("visibility","visible");
    $ventana.style.setProperty("opacity", "1");
}