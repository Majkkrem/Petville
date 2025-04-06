import React, { useState } from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import LoginModal from './LoginModal';
import 'bootstrap/dist/css/bootstrap.min.css';

const CustomNavbar: React.FC = () => {
  const [showLoginModal, setShowLoginModal] = useState(false);

  const handleLoginClick = () => {
    setShowLoginModal(true);
  };

  const handleCloseModal = () => {
    setShowLoginModal(false);
  };

  return (
    <>
      <Navbar bg="dark" variant="dark" expand="lg" className="navbar-dark">
        <Container fluid className="justify-content-center">
          <Navbar.Collapse id="navbarSupportedContent">
            <Nav className="mx-auto">
              <Nav.Link href="#game-description">About the game</Nav.Link>
              <Nav.Link href="#pictures">Pictures</Nav.Link>
              <Nav.Link href="#download">Download the game</Nav.Link>
              <Nav.Link href="#leaderboard">Leaderboard</Nav.Link>
              <Nav.Link onClick={handleLoginClick} style={{ cursor: 'pointer' }}>
                Login
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>

      <LoginModal 
        show={showLoginModal}
        onHide={handleCloseModal}
        onShow={handleLoginClick}
      />
    </>
  );
};

export default CustomNavbar;