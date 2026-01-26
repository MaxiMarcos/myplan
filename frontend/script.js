document.addEventListener('DOMContentLoaded', () => {
    const darkModeToggle = document.getElementById('darkModeToggle');
    const body = document.body;

    // Check for user's preferred theme in localStorage
    const currentTheme = localStorage.getItem('theme');
    if (currentTheme) {
        body.classList.add(currentTheme);
        if (currentTheme === 'dark-mode') {
            darkModeToggle.textContent = 'Modo Claro';
        }
    }

    darkModeToggle.addEventListener('click', () => {
        body.classList.toggle('dark-mode');
        if (body.classList.contains('dark-mode')) {
            darkModeToggle.textContent = 'Modo Claro';
            localStorage.setItem('theme', 'dark-mode');
        } else {
            darkModeToggle.textContent = 'Modo Oscuro';
            localStorage.setItem('theme', 'light-mode');
        }
    });
});
