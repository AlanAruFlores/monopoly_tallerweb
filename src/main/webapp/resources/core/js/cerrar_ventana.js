export function cerrarVentanaEmergente(ventanaEmergente){
    const $ventana = document.querySelector(ventanaEmergente);
    $ventana.style.setProperty("visibility","hidden");
    $ventana.style.setProperty("opacity", 0);

}