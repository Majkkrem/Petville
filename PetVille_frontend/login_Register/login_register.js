document.addEventListener('DOMContentLoaded', function() {
    // Bejelentkezési űrlap kezelése
    const loginForm = document.getElementById('loginForm');
    if (loginForm) {
      loginForm.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const email = document.getElementById('loginEmail').value.trim();
        const password = document.getElementById('loginPassword').value.trim();
        const errorElement = document.getElementById('loginError');
  
        // Validáció
        if (!email || !password) {
          showError(errorElement, 'Please fill in all fields');
          return;
        }
  
        // Teszt bejelentkezési adatok
        if (email === 'user@example.com' && password === 'Password123') {
          // Sikeres bejelentkezés
          window.location.href = 'index.html';
        } else {
          showError(errorElement, 'Invalid email or password');
        }
      });
    }
  
    // Regisztrációs űrlap kezelése
    const registerForm = document.getElementById('registerForm');
    if (registerForm) {
      registerForm.addEventListener('submit', function(event) {
        event.preventDefault();
        
        const email = document.getElementById('registerEmail').value.trim();
        const username = document.getElementById('registerUsername').value.trim();
        const password = document.getElementById('registerPassword').value.trim();
        const errorElement = document.getElementById('registerError');
  
        // Validáció
        if (!email || !username || !password) {
          showError(errorElement, 'Please fill in all fields');
          return;
        }
  
        // Jelszó komplexitás
        const passwordRegex = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z\d]{8,}$/;
        if (!passwordRegex.test(password)) {
          showError(errorElement, 'Password must be: 8+ chars, uppercase, lowercase and number');
          return;
        }
  
        // Felhasználó létrehozása
        createUser({ email, username, password }, errorElement);
      });
    }
  });
  
  // Hibák megjelenítése
  function showError(element, message) {
    if (element) {
      element.textContent = message;
      element.style.display = 'block';
      element.style.color = '#dc3545'; // Piros szín a hibához
      element.style.marginTop = '10px';
    }
  }
  
  // Felhasználó létrehozása (mock)
  function createUser(user, errorElement) {
    console.log('Registering user:', user);
    
    // Mock kérés - valós implementációban fetch lenne
    setTimeout(() => {
      const shouldFail = Math.random() > 0.8; // 20% eséllyel hibás
      if (shouldFail) {
        showError(errorElement, 'Registration failed. Please try again.');
      } else {
        if (errorElement) {
          errorElement.style.display = 'none';
        }
        alert('Registration successful! You can now log in.');
        document.getElementById('registerForm').reset();
      }
    }, 1000);
  }