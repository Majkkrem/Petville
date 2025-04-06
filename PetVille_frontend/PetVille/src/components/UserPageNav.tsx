import React, { useState } from 'react';
import { Navbar, Nav, Container } from 'react-bootstrap';
import LoginModal from './LoginModal';
import 'bootstrap/dist/css/bootstrap.min.css';

const CustomNavbar: React.FC = () => {
  const [showLoginModal, setShowLoginModal] = useState(false);

  const handleLogoutClick = () => {
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
              <Nav.Link href="#game-description">Home</Nav.Link>
              <Nav.Link href="#pictures">My Pets</Nav.Link>
              <Nav.Link onClick={handleLogoutClick} style={{ cursor: 'pointer' }}>
                Logout
              </Nav.Link>
            </Nav>
          </Navbar.Collapse>
        </Container>
      </Navbar>
    </>

  
  );
};

export default CustomNavbar;