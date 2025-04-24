import React, { createContext, useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../Api';

interface AuthContextType {
  isAuthenticated: boolean;
  userId: number | null;
  login: (email: string, password: string) => Promise<void>;
  logout: () => void;
}

enum Role {
  USER = "USER",
  ADMIN = "ADMIN"
}

interface User {
  id: number;
  name: string;
  email: string;
  password: string;
  role: Role;
  // These might be optional depending on your API response
  Leaderboard?: any[]; // Replace 'any' with proper Leaderboard type if available
  Save_files?: any[];  // Replace 'any' with proper Save_files type if available
  createdAt?: Date;    // Assuming you want to show when user joined
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userId, setUserId] = useState<number | null>(null);
  const navigate = useNavigate();

  const login = async (email: string, password: string) => {
    console.log('Bejelentkezés:', email);
    setIsAuthenticated(true);
    const response = await api.post<User>("/auth/loginWeb", JSON.stringify({ email, password }) );
    console.log(response.data.id);
    setUserId(response.data.id);
    navigate('/user');
  };

  const logout = () => {
    setIsAuthenticated(false);
    localStorage.removeItem('isAuthenticated');
    navigate('/');
  };

  React.useEffect(() => {
    const storedAuth = localStorage.getItem('isAuthenticated') === 'true';
    if (storedAuth) {
      setIsAuthenticated(true);
    }
  }, []);

  return (
    <AuthContext.Provider value={{ isAuthenticated, login, logout, userId }}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};



// sutik -> utana kell nezni
// user html, user tabla feltoltese (fetch), footer, css, logout gomb
// ne lepjen ki ha frissitunk az oldalra
