import React, { createContext, useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { api } from '../Api';
import { AxiosError } from 'axios';

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
  Leaderboard?: any[];
  Save_files?: any[];
  createdAt?: Date;
}

const AuthContext = createContext<AuthContextType | null>(null);

export const AuthProvider = ({ children }: { children: React.ReactNode }) => {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [userId, setUserId] = useState<number | null>(null);
  const navigate = useNavigate();

  const login = async (email: string, password: string) => {
    console.log('Bejelentkezés:', email);
    try {
      const response = await api.post<User>("/auth/loginWeb", JSON.stringify({ email, password }) );
      console.log(response.data.id);
      setUserId(response.data.id);
      setIsAuthenticated(true);
      navigate('/user');
    } catch (error) {
      throw error;
    }
  };

  const logout = async () => {
    try {
      await api.post("/auth/check");
      const response = await api.post("/auth/logout");
      setUserId(null);
      setIsAuthenticated(false);
      navigate('/');
    }catch (error) {
      console.error('Hiba a kijelentkezes során:', error);
    }
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