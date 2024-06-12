const datosDeLasPropiedadesDeLosUsuarios = JSON.parse(document.currentScript.getAttribute("datos-propiedades-usuarios"));
console.log(datosDeLasPropiedadesDeLosUsuarios);
mostrarPropiedadesConAliasUsuarioNombreEnLaWeb();

function mostrarPropiedadesConAliasUsuarioNombreEnLaWeb(){
    for (const elem of datosDeLasPropiedadesDeLosUsuarios) {
        const $pUsuarioNombre = `<p class="propiedad_alias" style="color:${elem.colorUsuario}">${elem.usuario.nombreUsuario}</p>`
        document.getElementById(elem.propiedad.nroCasilla).innerHTML+= $pUsuarioNombre;
    }
}
