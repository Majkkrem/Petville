import './home.css'
import javascriptLogo from './javascript.svg'
import viteLogo from '/vite.svg'
import { setupCounter } from './counter.js'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.bundle.min.js'

function fetchUsers() {
  fetch('http://localhost:3000/users')
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(function (data) {
      console.log(data);
    })
    .catch(function (error) {
      console.error('There was a problem with the fetch operation:', error);
    });
}

function createUser(user) {
  fetch('http://localhost:3000/users', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(user)
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(function (data) {
      console.log(data);
    })
    .catch(function (error) {
      console.error('There was a problem with the fetch operation:', error);
    });
}

function fetchLeaderboards() {
  fetch('http://localhost:3000/leaderboard')
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(function (data) {
      console.log(data);
    })
    .catch(function (error) {
      console.error('There was a problem with the fetch operation:', error);
    });
}

function createLeaderboard(leaderboard) {
  fetch('http://localhost:3000/leaderboard', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(leaderboard)
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(function (data) {
      console.log(data);
    })
    .catch(function (error) {
      console.error('There was a problem with the fetch operation:', error);
    });
}

function fetchSaveFiles() {
  fetch('http://localhost:3000/save-files')
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(function (data) {
      console.log(data);
    })
    .catch(function (error) {
      console.error('There was a problem with the fetch operation:', error);
    });
}

function createSaveFile(saveFile) {
  fetch('http://localhost:3000/save-files', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(saveFile)
  })
    .then(function (response) {
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      return response.json();
    })
    .then(function (data) {
      console.log(data);
    })
    .catch(function (error) {
      console.error('There was a problem with the fetch operation:', error);
    });
}

// Example usage
fetchUsers();
document.getElementById('createUserButton').addEventListener('click', function () {
  var newUser = { name: 'John Doe', email: 'john@example.com', password: 'password' };
  createUser(newUser);
});