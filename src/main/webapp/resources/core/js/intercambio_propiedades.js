import {abrirVentana,cerrarVentana} from "./cerrar_abrir_ventana.js";
/*
document.querySelector("#intercambio__boton").addEventListener("click",()=>{
    document.querySelector('.ventana__intercambio__propiedades').style.visibility = "visible";
    document.querySelector('.ventana__intercambio__propiedades').style.opacity = "1";
});

document.getElementById('close-popup').addEventListener('click', function() {
    document.querySelector('.ventana__intercambio__propiedades').style.visibility = "hidden";
    document.querySelector('.ventana__intercambio__propiedades').style.opacity = "0";
});
*/

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
                      <div class="franja" style="background-color:${propiedadUsuario.propiedad.color}"></div>
                      <div>
                        <p class="house-parrafo">${propiedadUsuario.propiedad.nombre} ${propiedadUsuario.propiedad.precio}"</p>
                      </div>
                    </div>`
                }
                document.querySelector("#jugador2Columna").innerHTML=`
                <div class="player-info">
                  <div class="profile">
                    <img src="/spring/${partidaUsuarioJson.usuario.imagenPerfil}" alt="Perfil Jugador 2" class="profile-img">
                    <span class="nombre">${partidaUsuarioJson.usuario.nombreUsuario}</span>
                    <div class="money">
                      <input type="number" pattern="[0-9]{4}" max="${partidaUsuarioJson.saldo}" placeholder="$">
                    </div>
                  </div>
                  <div class="selected-houses">
                    <div class="house-slot" data-player="jugador2" data-slot="1"></div>
                    <div class="house-slot" data-player="jugador2" data-slot="2"></div>
                  </div>
                  <div class="house-options">
                       ${partidaUsuarioListaHtml}
                  </div>
                </div>
                `;
            }
        })

    })
})


/*OTROS*/
document.querySelectorAll('.house-div').forEach(div => {
    div.addEventListener('click', function() {
        console.log(div.innerHTML);
        selectHouse(this.dataset.player, div);
    });
});

function selectHouse(player, div) {
    const slots = document.querySelectorAll(`.house-slot[data-player="${player}"]`);
    const emptySlot = Array.from(slots).find(slot => !slot.innerHTML);
    if (emptySlot) {
        emptySlot.innerHTML = div.innerHTML;
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
    alert('Intercambio realizado');
    cancel(); // Opcional: limpia los slots después del intercambio
}

function cancel() {
    const slots = document.querySelectorAll('.house-slot');

    slots.forEach(slot => {
        slot.innerHTML = ''
        slot.style.backgroundColor = "transparent";
    });


    document.querySelectorAll('.house-div').forEach(div => {
        div.style.display="flex";
    });
}