import React, { useState, useEffect } from 'react';
import CustomFooter from "./CustomFooter";
import CustomNavbar from "./CustomNav";
import 'bootstrap/dist/css/bootstrap.min.css';
import { api } from '../Api';
import { useAuth } from './AuthProvider';
import "../UserPage.css";

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
  Save_files?: SaveFile[];
  dateOfRegister?: Date;
}

interface SaveFile {
  id: number;
  user_id: number;
  petName: string;
  petType: string;
  petEnergy: number;
  petHunger: number;
  petMood: number;
  petHealth: number;
  hoursPlayer: number;
  goldEarned: number;
  currentGold: number;
}

interface UserStats {
  petsOwned: number;
  playTime: number;
}

function UserPage() {
  const [userData, setUserData] = useState<{
    user: User | null;
    userStats: UserStats | null;
  }>({
    user: null,
    userStats: null
  });
  const [activeTab, setActiveTab] = useState<string>('all');
  const [petCards, setPetCards] = useState<{
    all: SaveFile[];
    dog: SaveFile[];
    cat: SaveFile[];
    frog: SaveFile[];
    bee: SaveFile[];
  }>({
    all: [],
    dog: [],
    cat: [],
    frog: [],
    bee: []
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);
  const { userId } = useAuth();

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const userResponse = await api.get<User>(`/users/${userId}`, {
          headers: {
            'Content-Type': 'application/json',
          },
          withCredentials: true,
        });

        const saveFilesResponse = await api.get<SaveFile[]>(`/save-files/${userId}`);
        const pets = Array.isArray(saveFilesResponse.data) ? saveFilesResponse.data : [];

        setPetCards({
          all: pets,
          dog: pets.filter(pet => pet.petType.toLowerCase() === 'dog'),
          cat: pets.filter(pet => pet.petType.toLowerCase() === 'cat'),
          frog: pets.filter(pet => pet.petType.toLowerCase() === 'frog'),
          bee: pets.filter(pet => pet.petType.toLowerCase() === 'bee')
        });

        setUserData({
          user: userResponse.data,
          userStats: {
            petsOwned: pets.length,
            playTime: pets.reduce((total, file) => total + file.hoursPlayer, 0)
          }
        });
      } catch (err) {
        console.error('Error fetching user data:', err);
        setError('Failed to load user data');
      } finally {
        setLoading(false);
      }
    };

    if (userId) {
      fetchUserData();
    }
  }, [userId]);

  if (loading) {
    return (
      <div className="d-flex flex-column min-vh-100">
        <CustomNavbar />
        <main className="container-fluid flex-grow-1 py-5">
          <div className="text-center py-5">
            <div className="spinner-border text-primary" role="status">
              <span className="visually-hidden">Loading...</span>
            </div>
            <p className="mt-3">Loading user data...</p>
          </div>
        </main>
        <CustomFooter />
      </div>
    );
  }

  if (error) {
    return (
      <div className="d-flex flex-column min-vh-100">
        <CustomNavbar />
        <main className="container-fluid flex-grow-1 py-5">
          <div className="alert alert-danger text-center">
            Error loading user data: {error}
          </div>
        </main>
        <CustomFooter />
      </div>
    );
  }

  if (!userData.user) {
    return (
      <div className="d-flex flex-column min-vh-100">
        <CustomNavbar />
        <main className="container-fluid flex-grow-1 py-5">
          <div className="alert alert-warning text-center">
            No user data available
          </div>
        </main>
        <CustomFooter />
      </div>
    );
  }

  const joinDate = userData.user.dateOfRegister
    ? new Date(userData.user.dateOfRegister).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric'
    })
    : 'N/A';

  const renderPetCard = (pet: SaveFile) => (
    <div key={pet.id} className="col-md-6 col-lg-4 col-xl-3 mb-4">
      <div className="card h-100 shadow-sm border-0">
        <div className={`card-header bg-${pet.petType.toLowerCase()} text-white`}>
          <h5 className="card-title mb-0 text-capitalize">{pet.petName}</h5>
          <small className="text-white-50">{pet.petType}</small>
        </div>
        <div className="card-body">
          <div className="d-flex justify-content-between mb-2">
            <span>Coins:</span>
            <span className="fw-bold">{pet.currentGold}</span>
          </div>
          <div className="d-flex justify-content-between mb-2">
            <span>Play Time:</span>
            <span className="fw-bold">{pet.hoursPlayer} hours</span>
          </div>
          <div className="progress mt-3" style={{ height: '10px' }}>
            <div
              className="progress-bar bg-success"
              role="progressbar"
              style={{ width: `${Math.min(pet.petEnergy, 100)}%` }}
              aria-valuenow={pet.petEnergy}
              aria-valuemin={0}
              aria-valuemax={100}
            ></div>
          </div>
          <small className="text-muted">Energy: {pet.petEnergy}%</small>
        </div>
      </div>
    </div>
  );

  const renderNoPetsMessage = (type: string) => (
    <div className="col-12 text-center py-5">
      <div className="display-4 mb-3">🐾</div>
      <h4 className="text-muted">No {type} pets yet</h4>
      <p className="text-muted">Adopt a {type} to see them here!</p>
    </div>
  );

  return (
    <div className="user-page">
      <CustomNavbar />

      <main className="container-fluid flex-grow-1 py-3">
        <div className="row justify-content-center">
          <div className="col-lg-1 d-none d-lg-block"></div>

          <div className="col-lg-10 col-12">
            <div className="my-3 my-md-5">
              <section id="user-profile" className="mb-4 mb-md-5 text-center">
                <div className="card user-profile-card">
                  <div className="card-body">
                    <h2 className="mb-2">{userData.user.name}</h2>
                    <p className="text-muted mb-1">
                      <i className="bi bi-envelope-fill me-2"></i>
                      {userData.user.email}
                    </p>
                    <p className="text-muted">
                      <i className="bi bi-calendar-check me-2"></i>
                      Joined on: {joinDate}
                    </p>
                  </div>
                </div>
              </section>

              <section id="my-pets" className="mb-5">
                <div className="card-body">
                  <h3 className="mb-3 mb-md-4 text-center text-md-start section-title">My Stats</h3>
                  <div className="row g-4" id="pets-container">
                    <div className="col-md-6 col-12">
                      <div className="card h-100 stats-card">
                        <div className="card-body text-center">
                          <div className="stats-icon">🐕</div>
                          <h6 className="stats-label">Pets Owned</h6>
                          <p className="stats-value" id="pets-owned">{userData.userStats?.petsOwned ?? 0}</p>
                        </div>
                      </div>
                    </div>

                    <div className="col-md-6 col-12">
                      <div className="card h-100 stats-card">
                        <div className="card-body text-center">
                          <div className="stats-icon">⏱️</div>
                          <h6 className="stats-label">Total Play Time</h6>
                          <p className="stats-value" id="play-time">{userData.userStats?.playTime ?? 0} hours</p>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </section>

              {/* Enhanced Pets Gallery Section */}
              <section id="pets-gallery" className="mb-5">
                <div className="d-flex justify-content-between align-items-center mb-4">
                  <h3 className="mb-0">My Pets Collection</h3>
                  <div className="btn-group" role="group">
                    <button
                      type="button"
                      className={`btn btn-sm ${activeTab === 'all' ? 'btn-primary' : 'btn-outline-primary'}`}
                      onClick={() => setActiveTab('all')}
                    >
                      All Pets
                    </button>
                    <button
                      type="button"
                      className={`btn btn-sm ${activeTab === 'dog' ? 'btn-primary' : 'btn-outline-primary'}`}
                      onClick={() => setActiveTab('dog')}
                    >
                      Dogs
                    </button>
                    <button
                      type="button"
                      className={`btn btn-sm ${activeTab === 'cat' ? 'btn-primary' : 'btn-outline-primary'}`}
                      onClick={() => setActiveTab('cat')}
                    >
                      Cats
                    </button>
                    <button
                      type="button"
                      className={`btn btn-sm ${activeTab === 'frog' ? 'btn-primary' : 'btn-outline-primary'}`}
                      onClick={() => setActiveTab('frog')}
                    >
                      Frogs
                    </button>
                    <button
                      type="button"
                      className={`btn btn-sm ${activeTab === 'bee' ? 'btn-primary' : 'btn-outline-primary'}`}
                      onClick={() => setActiveTab('bee')}
                    >
                      Bees
                    </button>
                  </div>
                </div>

                <div className="row">
                  {petCards[activeTab as keyof typeof petCards].length > 0 ? (
                    petCards[activeTab as keyof typeof petCards].map(renderPetCard)
                  ) : (
                    renderNoPetsMessage(activeTab === 'all' ? '' : activeTab)
                  )}
                </div>
              </section>
            </div>
          </div>

          <div className="col-lg-1 d-none d-lg-block"></div>
        </div>
      </main>

      <CustomFooter />
    </div>
  );
}

export default UserPage;