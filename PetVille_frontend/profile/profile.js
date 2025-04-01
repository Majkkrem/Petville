document.addEventListener('DOMContentLoaded', async function() {
    // API végpontok
    const API_BASE_URL = 'http://localhost:3000';
    const PROFILE_URL = `${API_BASE_URL}/api/user/profile`;
    const LEADERBOARD_URL = `${API_BASE_URL}/api/leaderboard`;
    const LOGOUT_URL = `${API_BASE_URL}/api/auth/logout`;
  
    // Hitelesítési token lekérése
    const token = localStorage.getItem('authToken');
    
    if (!token) {
      window.location.href = 'login.html';
      return;
    }
  
    try {
      // Felhasználói profil adatok lekérése
      const profileResponse = await fetch(PROFILE_URL, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
  
      if (!profileResponse.ok) {
        throw new Error('Failed to load profile data');
      }
  
      const userData = await profileResponse.json();
  
      // Ranglista adatok lekérése
      const leaderboardResponse = await fetch(LEADERBOARD_URL, {
        headers: {
          'Authorization': `Bearer ${token}`
        }
      });
  
      if (!leaderboardResponse.ok) {
        throw new Error('Failed to load leaderboard data');
      }
  
      const leaderboardData = await leaderboardResponse.json();
  
      // Felhasználói adatok megjelenítése
      document.getElementById('username').textContent = userData.username || 'Guest';
      document.getElementById('user-email').textContent = userData.email || 'No email';
      document.getElementById('join-date').textContent = new Date(userData.createdAt).toLocaleDateString() || 'Unknown';
      document.getElementById('coin-count').textContent = userData.coins || 0;
      document.getElementById('achievement-count').textContent = userData.achievements?.length || 0;
      document.getElementById('play-time').textContent = `${Math.floor(userData.playTimeMinutes / 60)} hours` || '0 hours';
      document.getElementById('pets-owned').textContent = userData.pets?.length || 0;
      document.getElementById('high-score').textContent = userData.highScore || 0;
  
      // Háziállatok megjelenítése
      const petsContainer = document.getElementById('pets-container');
      if (userData.pets && userData.pets.length > 0) {
        petsContainer.innerHTML = '';
        userData.pets.forEach(pet => {
          const petCard = document.createElement('div');
          petCard.className = 'col-md-6 mb-4';
          petCard.innerHTML = `
            <div class="card pet-card">
              <div class="card-body">
                <div class="row align-items-center">
                  <div class="col-4">
                    <img src="assets/${pet.type || 'pet'}.png" alt="${pet.type || 'Pet'}" class="img-fluid pet-img">
                  </div>
                  <div class="col-8">
                    <h5>${pet.name || 'Unnamed'}</h5>
                    <p class="mb-1">Type: ${pet.type || 'Unknown'}</p>
                    <p class="mb-1">Level: ${pet.level || 1}</p>
                    <div class="progress mb-2" style="height: 10px;">
                      <div class="progress-bar bg-success" role="progressbar" 
                           style="width: ${pet.happiness || 50}%" 
                           aria-valuenow="${pet.happiness || 50}" 
                           aria-valuemin="0" 
                           aria-valuemax="100"></div>
                    </div>
                    <small>Happiness: ${pet.happiness || 50}%</small>
                  </div>
                </div>
              </div>
            </div>
          `;
          petsContainer.appendChild(petCard);
        });
      } else {
        petsContainer.innerHTML = `
          <div class="col-12 text-center">
            <div class="alert alert-info">You don't have any pets yet. <a href="game.html">Start playing!</a></div>
          </div>
        `;
      }
  
      // Ranglista megjelenítése
      const leaderboardBody = document.getElementById('leaderboard-body');
      if (leaderboardData && leaderboardData.length > 0) {
        leaderboardBody.innerHTML = '';
        leaderboardData.forEach(player => {
          const row = document.createElement('tr');
          if (player.username === userData.username) {
            row.classList.add('table-primary');
          }
          row.innerHTML = `
            <th scope="row">${player.rank}</th>
            <td>${player.username}</td>
            <td>${player.score}</td>
            <td>${player.petsCount || 0}</td>
          `;
          leaderboardBody.appendChild(row);
        });
      }
  
    } catch (error) {
      console.error('Error loading profile:', error);
      document.getElementById('pets-container').innerHTML = `
        <div class="col-12 text-center">
          <div class="alert alert-danger">Failed to load profile data. Please try again later.</div>
        </div>
      `;
    }
  
    // Kijelentkezés
    document.getElementById('logout-btn').addEventListener('click', async function() {
      try {
        await fetch(LOGOUT_URL, {
          method: 'POST',
          headers: {
            'Authorization': `Bearer ${token}`
          }
        });
        
        localStorage.removeItem('authToken');
        window.location.href = 'index.html';
      } catch (error) {
        console.error('Logout failed:', error);
        alert('Logout failed. Please try again.');
      }
    });
  });