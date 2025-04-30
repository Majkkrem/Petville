import React, { useState } from 'react';
import { Alert, Button, Form, Modal, Spinner } from 'react-bootstrap';
import '../modals.css';

interface RegisterModalProps {
  show: boolean;
  onHide: () => void;
  onRegistrationSuccess: (email: string) => void;
}

const RegisterModal: React.FC<RegisterModalProps> = ({
  show,
  onHide,
  onRegistrationSuccess
}) => {
  const [email, setEmail] = useState('');
  const [name, setName] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');
  const [isSubmitting, setIsSubmitting] = useState(false);

  const validatePassword = (pwd: string): boolean => {
    if (pwd.length < 8) {
      return false;
    }
    if (!/[A-Z]/.test(pwd)) {
      return false;
    }
    if (!/[0-9]/.test(pwd)) {
      return false;
    }
    return true;
  };

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();

    if (!validatePassword(password)) {
      setError('Password must contain: at least 8 characters, 1 uppercase letter and 1 number');
      return;
    }

    setIsSubmitting(true);
    try {
      const response = await fetch(`http://localhost:3000/users`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({ 
          email, 
          name, 
          password 
        }),
      });

      const data = await response.json();
      
      if (!response.ok) {
        if(response.status === 500) {
          throw new Error('Server error. Please try again later.');
        }
        if(response.status === 400) {
          if(data.message === 'Users_name_key') {
            throw new Error('Username already exists.');
          }
          else if(data.message === 'Users_email_key') {
            throw new Error('Email already exists.');
          }
        }
        throw new Error(data.message || 'Registration failed');
      }

      alert('Registration successful! You can now login.');
      onRegistrationSuccess(email); 
      onHide();
    } catch (err) {
      setError(err instanceof Error ? err.message : 'An error occurred during registration');
      console.error('Registration error:', err);
    } finally {
      setIsSubmitting(false);
    }
  };

  return (
    <Modal show={show} onHide={onHide}>
      <Modal.Header closeButton id='modalHeader'>
        <Modal.Title>Registration</Modal.Title>
      </Modal.Header>
      <Modal.Body id='modalBody'>
        <Form onSubmit={handleSubmit}>
          <Form.Group className="mb-3">
            <Form.Label>Email address</Form.Label>
            <Form.Control
              type="email" 
              id='registerEmail'
              placeholder="Enter email" 
              value={email}
              onChange={(e) => {
                setEmail(e.target.value);
                setError('');
              }}
              required
              disabled={isSubmitting}
            />
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Username</Form.Label>
            <Form.Control 
              type="text" 
              id='registerUsername'
              placeholder="Username" 
              value={name}
              onChange={(e) => {
                setName(e.target.value);
                setError('');
              }}
              required
              disabled={isSubmitting}
            />
            <Form.Text className="text-muted">
              Must be at least 3 characters long
            </Form.Text>
          </Form.Group>

          <Form.Group className="mb-3">
            <Form.Label>Password</Form.Label>
            <Form.Control 
              type="password" 
              id='registerPassword'
              placeholder="Password" 
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required 
              disabled={isSubmitting}
            />
            <Form.Text className="text-muted">
              Password must contain: at least 8 characters, 1 uppercase letter and 1 number
            </Form.Text>
          </Form.Group>
          
          {error && (
            <Alert variant="danger" className="mt-3">
              {error}
            </Alert>
          )}
        </Form>
      </Modal.Body>
      <Modal.Footer id='modalFooter'>
        <Button variant="secondary" onClick={onHide} disabled={isSubmitting}>
          Close
        </Button>
        <Button 
          variant="primary"
          type="submit"
          disabled={!email || !name || !password || isSubmitting}
          onClick={handleSubmit}
        >
          {isSubmitting ? (
            <>
              <Spinner animation="border" size="sm" className="me-2" />
              Registering...
            </>
          ) : 'Register'}
        </Button>
      </Modal.Footer>
    </Modal>
  );
};

export default RegisterModal;