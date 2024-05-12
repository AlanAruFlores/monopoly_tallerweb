export function reproducir($iconAudio,audioMusica) {
    audioMusica.play();
    $iconAudio.classList.remove("fa-music-note-slash");
    $iconAudio.classList.add("fa-music-note");
}

export function pausar($iconAudio,audioMusica){
    audioMusica.pause();
    $iconAudio.classList.remove("fa-music-note");
    $iconAudio.classList.add("fa-music-note-slash");
}