import React from 'react';
import { Navbar, Nav, Container, Button } from 'react-bootstrap';
import { useNavigate } from 'react-router-dom';

const CustomNavbar: React.FC = () => {
  const navigate = useNavigate();

  const handleLogoutClick = () => {
    navigate('/');
  };

  return (
    <Navbar bg="dark" variant="dark" expand="lg" collapseOnSelect>
      <Container fluid>
        <Navbar.Brand href="#home" className="me-auto">PetVille</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="ms-auto">
            <Nav.Link href="#game-description" className="px-3">Home</Nav.Link>
            <Nav.Link href="#pictures" className="px-3">My Pets</Nav.Link>
            <Button 
              variant="outline-light" 
              onClick={handleLogoutClick} 
              className="ms-lg-3 mt-2 mt-lg-0"
            >
              Logout
            </Button>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default CustomNavbar;