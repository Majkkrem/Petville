import React, { useState, useEffect } from 'react';
import "../HomePage.css";
import CustomFooter from "./CustomFooter";
import CustomNavbar from "./CustomNav";
import { api } from '../Api';

interface LeaderboardEntry {
  rank: number;
  name: string;
  coins: number;
  petType?: string;
  avatar?: string;
}

interface LeaderboardResponse {
  id: number;
  user_id: number;
  score: number;
  save_file_id: number;
  save_file: {
    petType: string;
  };
  user: {
    name: string;
  };
}

function HomePage() {
  const [leaderboardData, setLeaderboardData] = useState<LeaderboardEntry[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchLeaderboard = async () => {
      try {
        const response = await api.get<LeaderboardResponse[]>('/leaderboard');
        console.log(response.data);
  
        const processedData: LeaderboardEntry[] = response.data
          .map(entry => ({
            name: entry.user.name || `Player ${entry.user_id}`,
            coins: entry.score,
            petType: entry.save_file.petType,
            rank: 0,
          }))
          .sort((a, b) => b.coins - a.coins)
          .map((entry, index) => ({
            ...entry,
            rank: index + 1,
            avatar: getAvatarByPetType(entry.petType)
          }))
          .slice(0, 5);
  
        setLeaderboardData(processedData);
      } catch (err) {
        console.error('Error fetching leaderboard:', err);
        setError('Failed to load leaderboard');
      } finally {
        setLoading(false);
      }
    };
  
    fetchLeaderboard();
  }, []);

  const getAvatarByPetType = (petType?: string) => {
    switch (petType?.toLowerCase()) {
      case 'dog': return '🐕';
      case 'cat': return '🐈';
      case 'bee': return '🐝';
      case 'frog': return '🐸';
      default: return '🐾';
    }
  };

  const getMedalIcon = (rank: number) => {
    switch (rank) {
      case 1: return '🥇';
      case 2: return '🥈';
      case 3: return '🥉';
      default: return `#${rank}`;
    }
  };

  return (
    <div className="home-page">
      <CustomNavbar />

      <div className="container-fluid">
        <div className="row justify-content-center">
          <div className="col-2 side-border-left"></div>
          <div className="col-8 main-content">
            <main className="my-4" id='main-content'>
              <section id="game-description" className="mb-5">
                <div className="home-title">
                  <h1 className="game-title">PetVille</h1>
                  <p className="game-tagline">Your virtual pet adventure begins here!</p>
                </div>
                
                <div className="about-section">
                  <h2 className="section-title">
                    <span className="title-icon">🐾</span> About the Game
                  </h2>
                  <div className="about-content">
                    <p>
                      PetVille is a free-to-play virtual pet game where you can adopt and care for adorable pets 
                      like dogs, cats, bees and frogs - and you can even own multiple pets at the same time!
                       Manage their needs, play games, and build your perfect pet paradise!
                    </p>
                    
                    <div className="feature-cards">
                      <div className="feature-card">
                        <div className="feature-icon">🦴</div>
                        <h3>Care for Pets</h3>
                        <p>Your goal is to take care of your chosen pet(s) by managing their needs, including hunger, energy, mood, and health.</p>
                      </div>
                      
                      <div className="feature-card">
                        <div className="feature-icon">🎮</div>
                        <h3>Play Games</h3>
                        <p>Earn coins through fun mini-games in the playground</p>
                      </div>
                      
                      <div className="feature-card">
                        <div className="feature-icon">🛍️</div>
                        <h3>Shop Items</h3>
                        <p>Buy food, potions, and more to care for your pets</p>
                      </div>
                    </div>
                  </div>
                </div>

                <div className="bars-section">
                  <h2 className="section-title">
                    <span className="title-icon">📊</span> About the Bars
                  </h2>
                  <div className="bars-grid">
                    <div className="bar-item">
                      <h3>Energy <span className="bar-icon">⚡</span></h3>
                      <p>Decreases when your pet is tired. Restore energy by letting your pet sleep in the bedroom or by feeding them energy bars in the kitchen.</p>
                    </div>
                    <div className="bar-item">
                      <h3>Food <span className="bar-icon">🍗</span></h3>
                      <p>Decreases when your pet is hungry. Refill it by feeding them various foods in the kitchen.</p>
                    </div>
                    <div className="bar-item">
                      <h3>Health <span className="bar-icon">❤️</span></h3>
                      <p>Decreases when your pet becomes sick. Improve their health by giving them medicine in the kitchen</p>
                    </div>
                    <div className="bar-item">
                      <h3>Mood <span className="bar-icon">😊</span></h3>
                      <p>Decreases when your pet is bored. Boost their mood by playing mini-games in the playground, where you can also earn coins.</p>
                    </div>
                  </div>
                </div>

                <div className="coins-section">
                  <h2 className="section-title">
                    <span className="title-icon">🪙</span> About Coins
                  </h2>
                  <p>
                    Earn coins by playing mini-games and use them to buy items in the shop. <br /> <br />
                    Currently available exclusively for PC.
                  </p>
                </div>
              </section>

              <section id="pictures" className="mb-5 gallery-section">
                <h2 className="section-title">
                  <span className="title-icon">📸</span> Gallery
                </h2>
                <div className="gallery-grid">
                  <div className="gallery-placeholder"><img src="./pictures/bee.png" alt="" /></div>
                  <div className="gallery-placeholder"><img src="./pictures/dog.png" alt="" /></div>
                  <div className="gallery-placeholder"><img src="./pictures/cat.png" alt="" /></div>
                  <div className="gallery-placeholder"><img src="./pictures/frog.png" alt="" /></div>
                </div>
              </section>

              <section id="download" className="mb-5 download-section">
                <div className="download-card">
                  <h2 className="section-title">
                    <span className="title-icon">⬇️</span> Download Now
                  </h2>
                  <p>Join the PetVille adventure today!</p>
                  <button className="download-btn">
                    Download for PC
                    <span className="download-icon">💾</span>
                  </button>
                </div>
              </section>

              <section id="leaderboard" className="mb-4">
                <h3 className="text-center mb-4">
                  <span className="leaderboard-title">🏆 Top Players 🏆</span>
                </h3>

                {loading ? (
                  <div className="text-center py-4">
                    <div className="spinner-border text-primary" role="status">
                      <span className="visually-hidden">Loading...</span>
                    </div>
                  </div>
                ) : error ? (
                  <div className="alert alert-danger text-center">
                    {error}
                  </div>
                ) : (
                  <div className="leaderboard-container">
                    {leaderboardData.map((player, index) => (
                      <div key={index} className={`leaderboard-item ${index < 3 ? 'podium' : ''}`}>
                        <div className="leaderboard-rank">
                          <span className="medal">{getMedalIcon(player.rank)}</span>
                        </div>
                        <div className="leaderboard-avatar">
                          <div className="avatar-placeholder">
                            {player.avatar || getAvatarByPetType(player.petType)}
                          </div>
                        </div>
                        <div className="leaderboard-name">
                          {player.name}
                        </div>
                        <div className="leaderboard-coins">
                          <span className="coin-icon">🪙</span>
                          {player.coins.toLocaleString()}
                        </div>
                        <div className="leaderboard-progress">
                          <div
                            className="progress-bar"
                            style={{
                              width: `${Math.min(100, (player.coins / (leaderboardData[0]?.coins || 1)) * 100)}%`
                            }}
                          ></div>
                        </div>
                      </div>
                    ))}

                    {leaderboardData.length === 0 && (
                      <div className="text-center py-4 text-muted">
                        No leaderboard data available yet
                      </div>
                    )}
                  </div>
                )}
              </section>
            </main>
          </div>
          <div className="col-2 side-border-right"></div>
        </div>
      </div>

      <CustomFooter />
    </div>
  );
}

export default HomePage;