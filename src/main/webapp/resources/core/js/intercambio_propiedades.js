
document.querySelector("#intercambio__boton").addEventListener("click",()=>{
    document.querySelector('.ventana__intercambio__propiedades').style.visibility = "visible";
    document.querySelector('.ventana__intercambio__propiedades').style.opacity = "1";
});

document.getElementById('close-popup').addEventListener('click', function() {
    document.querySelector('.ventana__intercambio__propiedades').style.visibility = "hidden";
    document.querySelector('.ventana__intercambio__propiedades').style.opacity = "0";
});


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