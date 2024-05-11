export function abrirChat(ventanaChat){
    const $ventanaChatElemento = document.querySelector(ventanaChat);
    $ventanaChatElemento.style.setProperty("visibility","visible");
    $ventanaChatElemento.style.setProperty("opacity","1");
}

export function cerrarChat(ventanaChat) {
    const $ventanaChatElemento = document.querySelector(ventanaChat);
    $ventanaChatElemento.style.setProperty("visibility", "hidden");
    $ventanaChatElemento.style.setProperty("opacity", "0");
}
