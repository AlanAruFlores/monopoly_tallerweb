export function cerrarVentana(ventanaEmergente){
    const $ventana = document.querySelector(ventanaEmergente);
    $ventana.style.setProperty("visibility","hidden");
    $ventana.style.setProperty("opacity", "0");
}
export function abrirVentana(ventanaEmergente){
    const $ventana = document.querySelector(ventanaEmergente);
    $ventana.style.setProperty("visibility","visible");
    $ventana.style.setProperty("opacity", "1");
}