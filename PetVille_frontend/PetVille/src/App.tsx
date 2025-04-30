import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import UserPage from './components/UserPage';
import { AuthProvider } from './components/AuthProvider';
import CustomNavbar from './components/CustomNav';

function App() {
  return (
    <Router>
      <AuthProvider>
        <CustomNavbar />
        
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/home" element={<HomePage />} />
          <Route path="/user" element={<UserPage />} />
        </Routes>
      </AuthProvider>
    </Router>
  );
}

export default App;