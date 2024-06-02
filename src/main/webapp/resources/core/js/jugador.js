/*Extraigo el atributo "posicion" del ModelAndView
*/
let arrJugadoresJugando = JSON.parse(document.currentScript.getAttribute("jugadores"));
console.log(arrJugadoresJugando);

/*Creo a los jugadores con sus divs correspondientes*/
let indiceJugador =  1;
for(const jugadorItem of arrJugadoresJugando){
    ubicarJugador(jugadorItem);
}

function ubicarJugador(jugador){
    /*Ubico al jugador en el casillero*/
    posicion = jugador.posicionCasilla;
    casilla = document.getElementById(posicion);

    const jugadorElemento = document.createElement("div");
    jugadorElemento.classList.add("jugador");
    jugadorElemento.setAttribute("id", "jugador"+indiceJugador);
    indiceJugador++;
    jugadorElemento.style.setProperty("background-color", obtenerColorHexadecimal(jugador.colorUsuario));
    jugadorElemento.innerHTML = `
    <div class="rueda rueda1"></div>
    <div class="rueda rueda2"></div>
    <div class="rueda rueda3"></div>
    <div class="rueda rueda4"></div>
    <div class="techo">
    </div>
    `;
    /*Lo inserto en dentro de la casilla*/
    casilla.insertAdjacentElement("beforeend", jugadorElemento);

}

function obtenerColorHexadecimal(color){
    let hexadecimal ="";
    switch(color){
        case "ROJO":
            hexadecimal = "#f80000";
        break;
        case "VERDE":
            hexadecimal = "#258d19";
            break;
        case "AZUL":
            hexadecimal = "#107acc";
            break;
        case "ROSA":
            hexadecimal = "#d5408e";
            break;

    }
    return hexadecimal;
}