import React, { useState, useEffect } from 'react';
import CustomFooter from "./CustomFooter";
import CustomNavbar from "./UserPageNav";
import 'bootstrap/dist/css/bootstrap.min.css';
import { api } from '../Api';
import { useAuth } from './AuthProvider';


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

interface UserStats {
  petsOwned: number;
  highScore: number;
  playTime: number;
}

function UserPage() {
    const [userData, setUserData] = useState<{
        user: User | null;
        stats: UserStats;
    }>({
        user: null,
        stats: {
            petsOwned: 0,
            highScore: 0,
            playTime: 0
        }
    });
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState<string | null>(null);
    const { userId } = useAuth();

    useEffect(() => {
        const fetchUserData = async () => {
            try {
                // Fetch user profile
                console.log(userId);
                const userResponse = await api.get<User>(`/users/${userId}`, {
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    withCredentials: true,
                }
                );
                // Fetch user stats (assuming separate endpoint)
                const statsResponse = await api.get<UserStats>(`/save-files/${userId}`);
                
                setUserData({
                    user: userResponse.data,
                    stats: statsResponse.data
                });
            } catch (err) {
                console.error('Error fetching user data:', err);
            } finally {
                setLoading(false);
            }
        };

        fetchUserData();
    }, []);

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

    // Format join date
    const joinDate = userData.user.createdAt 
        ? new Date(userData.user.createdAt).toLocaleDateString('en-US', {
            year: 'numeric',
            month: 'long',
            day: 'numeric'
          })
        : 'N/A';

    return (
        <div className="d-flex flex-column min-vh-100">
            <CustomNavbar />
            
            <main className="container-fluid flex-grow-1 py-3">
                <div className="row justify-content-center">
                    <div className="col-lg-1 d-none d-lg-block"></div>
                    
                    <div className="col-lg-10 col-12">
                        <div className="my-3 my-md-5">
                            <section id="user-profile" className="mb-4 mb-md-5 text-center">
                                <div className="profile-header">
                                    <h2 id="username" className="display-5">{userData.user.name}</h2>
                                    <div className="badge bg-primary p-2 mt-2">
                                        Member since: <span id="join-date">{joinDate}</span>
                                    </div>
                                </div>
                            </section>

                            <section id="my-pets" className="mb-5">
                                <h3 className="mb-3 mb-md-4 text-center text-md-start">My Stats</h3>
                                <div className="row g-3" id="pets-container">
                                    <div className="col-md-4 col-12">
                                        <div className="card h-100 shadow-sm">
                                            <div className="card-body text-center">
                                                <h6 className="card-subtitle mb-2 text-muted">Pets Owned</h6>
                                                <p className="h4" id="pets-owned">{userData.stats.petsOwned}</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-md-4 col-12">
                                        <div className="card h-100 shadow-sm">
                                            <div className="card-body text-center">
                                                <h6 className="card-subtitle mb-2 text-muted">High Score</h6>
                                                <p className="h4" id="high-score">{userData.stats.highScore}</p>
                                            </div>
                                        </div>
                                    </div>

                                    <div className="col-md-4 col-12">
                                        <div className="card h-100 shadow-sm">
                                            <div className="card-body text-center">
                                                <h6 className="card-subtitle mb-2 text-muted">Total Play Time</h6>
                                                <p className="h4" id="play-time">{userData.stats.playTime} hours</p>
                                            </div>
                                        </div>
                                    </div>
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