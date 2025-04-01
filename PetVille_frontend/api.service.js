const API_BASE_URL = 'http://localhost:3000';

// Általános fetch függvény
async function fetchApi(endpoint, method = 'GET', data = null) {
  const options = {
    method,
    headers: {
      'Content-Type': 'application/json',
    },
  };

  if (data) {
    options.body = JSON.stringify(data);
  }

  const response = await fetch(`${API_BASE_URL}/${endpoint}`, options);
  
  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'API request failed');
  }

  return response.json();
}

// User kezelés
export const userService = {
  login: async (email, password) => {
    return fetchApi('users/login', 'POST', { email, password });
  },
  register: async (userData) => {
    return fetchApi('users', 'POST', userData);
  },
  getUsers: async () => {
    return fetchApi('users');
  },
  getUser: async (id) => {
    return fetchApi(`users/${id}`);
  }
};

// Leaderboard kezelés
export const leaderboardService = {
  getLeaderboard: async () => {
    return fetchApi('leaderboard');
  },
  addScore: async (scoreData) => {
    return fetchApi('leaderboard', 'POST', scoreData);
  }
};

// Save files kezelés
export const saveFileService = {
  getSaveFiles: async () => {
    return fetchApi('save-files');
  },
  getSaveFile: async (id) => {
    return fetchApi(`save-files/${id}`);
  },
  createSaveFile: async (saveFileData) => {
    return fetchApi('save-files', 'POST', saveFileData);
  },
  updateSaveFile: async (id, updateData) => {
    return fetchApi(`save-files/${id}`, 'PATCH', updateData);
  },
  deleteSaveFile: async (id) => {
    return fetchApi(`save-files/${id}`, 'DELETE');
  }
};