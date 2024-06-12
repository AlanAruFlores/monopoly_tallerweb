document.getElementById('open-popup').addEventListener('click', function() {
    document.getElementById('popup').style.display = 'block';
});

document.getElementById('close-popup').addEventListener('click', function() {
    document.getElementById('popup').style.display = 'none';
});

document.querySelectorAll('.house-img').forEach(img => {
    img.addEventListener('click', function() {
        selectHouse(this.dataset.player, this.src);
    });
});

function selectHouse(player, houseSrc) {
    const slots = document.querySelectorAll(`.house-slot[data-player="${player}"]`);
    const emptySlot = Array.from(slots).find(slot => !slot.style.backgroundImage);
    if (emptySlot) {
        emptySlot.style.backgroundImage = `url(${houseSrc})`;
        emptySlot.style.backgroundSize = 'cover';
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
    slots.forEach(slot => slot.style.backgroundImage = '');
}
