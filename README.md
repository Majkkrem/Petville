# Petzy_game
Fejlesztői dokumentáció

Klónozzuk le a repository-t: https://github.com/Majkkrem/Petville
git bash-ben/ vagy powershell-ben:
 git clone https://github.com/Majkkrem/Petville

Létrejön 3 mappa:
•	PetVille_frontend -> frontend react ts
  o	Megnyitáskor terminálba npm i
    	Majd npm i react-bootstrap
    	És npm i bootstrap
    	Elindítani npm run dev
•	Petville_Backend -> backend nest.js
  o	Megnyitás után npm i
  o	.env fájl létrehozása:
    	DATABASE_URL="mysql://root@localhost:3306/petville" 
    	JWT_SECRET = "ide generáltatni egy kódot" ADMIN_USERNAME = "admin_username" ADMIN_PASSWORD = "admin_password"
    	Elindítani npm run start:dev
•	Petzy -> Java
  o	Ebben semmi további dolog nincsen, csak futtatni a kódot
