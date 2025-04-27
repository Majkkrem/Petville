import "../HomePage.css";
import CustomFooter from "./CustomFooter";
import CustomNavbar from "./CustomNav";

function HomePage() {
  return (
    <div>
      <CustomNavbar />
      
      <div className="container-fluid">
        <div className="row justify-content-center">
          <div className="col-2 side-border-left"></div>
          <div className="col-8 main-content">
            <main className="my-4">
              <section id="game-description" className="mb-4">
                <h1>PetVille</h1>
                <br />
                <p id="about-game">
                  <i>About the game</i> <br />
                  PetVille is a free to play game, what is similar to Pou or Tamagotchi. 
                  In this game you can own a virtual pet like a dog, a cat, a bunny or a frog, but also you can own more pets at the same time. <br/> <br/>

                  You have to take care of your choosen pet or pets, because it can get hungry, tired, bored or sick. <br/>
                  During the game, if all of the bars are low, your pet will be very sick and the game will be over, which
                  means you have to start over the game and all the progress will be lost. <br/> <br/>

                  <i>About the bars</i> <br />
                  The energy bar will be low if your pet is tired. You can increase the energy bar by sending your pet to
                  sleep in the bedroom or giving them energy bars in the kitchen. <br/>
                  The food bar will be low if your pet is hungry. You can increase the food bar by giving them different types of foods in the kitchen. <br/>
                  The health bar will be low if your pet is feeling sick. You can increase the health bar by giving them
                  medicine in the kitchen. <br />
                  The mood bar will be low if your pet is bored. You can increase the mood bar by playing mini games in the playground where you can also earn coins. <br /><br/>

                  <i>Coins</i> <br />
                  You can earn coins by playing mini games in the playground. You can spend the coins in the shop, where you can buy different types of foods, potion and energy bars. <br/><br/>
                  At the moment the game is available only for PC.

                  
                </p>
              </section>

              <section id="pictures" className="mb-4">
                <h3>Pictures</h3>
                <br />
              </section>

              <section id="download" className="mb-4">
                <h3>Download the Game</h3>
                <br />
                <p id="download_text">Click the button to download the game for PC</p>
                <button id="download_button" className="btn btn-primary">Download</button>
              </section>

              <section id="leaderboard" className="mb-4">
                <h3>Leaderboard</h3>
                <br />
                <table className="table table-striped">
                  <thead>
                    <tr>
                      <th scope="col">#</th>
                      <th scope="col">Name</th>
                      <th scope="col">Score</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <th scope="row">1</th>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr>
                      <th scope="row">2</th>
                      <td></td>
                      <td></td>
                    </tr>
                    <tr>
                      <th scope="row">3</th>
                      <td></td>
                      <td></td>
                    </tr>
                  </tbody>
                </table>
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