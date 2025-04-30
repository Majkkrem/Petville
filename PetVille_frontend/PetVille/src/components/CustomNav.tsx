import React from 'react';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';
import { useAuth } from './AuthProvider';
import LoginModal from './LoginModal';

const CustomNavbar: React.FC = () => {
  const [showLoginModal, setShowLoginModal] = React.useState(false);
  const { isAuthenticated, logout } = useAuth();
  const navigate = useNavigate();

  const handleLoginClick = () => {
    setShowLoginModal(true);
  };

  const handleCloseModal = () => {
    setShowLoginModal(false);
  };

  const scrollToSection = (id: string) => {
    const element = document.getElementById(id);
    if (element) {
      element.scrollIntoView({ behavior: 'smooth' });
    }
  };

  return (
    <>
      <Navbar bg="dark" variant="dark" expand="lg" fixed="top">
        <Container fluid>
          <Navbar.Brand>
            <img src="./pictures/Logo.png" alt="Logo"
            style={
              { width: '50px', height: '50px' } 
            } />
          </Navbar.Brand>
          
          <Navbar.Toggle aria-controls="main-navbar" />
          
          <Navbar.Collapse id="main-navbar">
            <Nav className="ms-auto">
              {isAuthenticated ? (
                <>
                  <Nav.Link onClick={() => navigate('/home')}>Home</Nav.Link>
                  <Nav.Link onClick={() => navigate('/user')}>My profile</Nav.Link>
                  <Button 
                    variant="outline-light" 
                    onClick={logout}
                    className="ms-2"
                  >
                    Logout
                  </Button>
                </>
              ) : (
                <>
                  <Nav.Link onClick={() => scrollToSection('game-description')}>Home</Nav.Link>
                  <Nav.Link onClick={() => scrollToSection('pictures')}>Pictures</Nav.Link>
                  <Nav.Link onClick={() => scrollToSection('download')}>Download Game</Nav.Link>
                  <Nav.Link onClick={() => scrollToSection('leaderboard')}>Leaderboard</Nav.Link>
                  <Button 
                    variant="outline-light" 
                    onClick={() => setShowLoginModal(true)}
                    className="ms-2"
                  >
                    Login
                  </Button>
                </>
              )}
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      {!isAuthenticated && (
        <LoginModal 
        show={showLoginModal}
        onHide={handleCloseModal}
        onShow={handleLoginClick}
        />
      )}
    </>
  );
};

export default CustomNavbar;