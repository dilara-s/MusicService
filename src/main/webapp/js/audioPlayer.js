let songs = []; // Массив песен
let currentSongIndex = 0; // Индекс текущей песни
let audioPlayer = document.getElementById('audio-player');  // Аудиоплеер
let audioSource = document.getElementById('audio-source');  // Источник для аудиофайла
let currentSongTitle = document.getElementById('current-song-title'); // Название песни

document.addEventListener("DOMContentLoaded", function() {
    // Инициализация песен из кнопок Play
    const playButtons = document.querySelectorAll(".play-btn");

    // Заполняем массив песен
    playButtons.forEach(function(button, index) {
        const song = {
            id: button.getAttribute("data-song-id"),
            title: button.closest(".song-card").querySelector("h5").textContent,
            artist: button.closest(".song-card").querySelector("p").textContent,
            audioUrl: button.getAttribute("data-audio-url")
        };
        songs.push(song);

        // Добавляем обработчик для кнопки Play
        button.addEventListener("click", function() {
            currentSongIndex = index; // Устанавливаем индекс текущей песни
            playSong(songs[currentSongIndex]); // Воспроизводим песню
        });
    });

    // Обработчик окончания воспроизведения песни
    audioPlayer.addEventListener("ended", function() {
        nextSong(); // Переход к следующей песне
    });
});

// Функция для воспроизведения песни
function playSong(song) {
    audioSource.src = song.audioUrl;  // Устанавливаем новый источник аудиофайла
    audioPlayer.load();  // Загружаем новый файл
    audioPlayer.play();  // Воспроизводим песню

    // Обновляем информацию о текущей песне
    currentSongTitle.textContent = `Now Playing: ${song.title} by ${song.artist}`;
}

// Функция для перехода к следующей песне
function nextSong() {
    currentSongIndex++;
    if (currentSongIndex >= songs.length) {
        currentSongIndex = 0; // Если песня была последней, начинаем с первой
    }
    playSong(songs[currentSongIndex]); // Воспроизводим следующую песню
}

// Функция для перехода к предыдущей песне (по желанию)
function prevSong() {
    currentSongIndex--;
    if (currentSongIndex < 0) {
        currentSongIndex = songs.length - 1; // Если песня была первой, начинаем с последней
    }
    playSong(songs[currentSongIndex]); // Воспроизводим предыдущую песню
}
