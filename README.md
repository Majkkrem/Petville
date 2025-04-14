# Petville
## Fejlesztői dokumentáció

Klónozzuk le a  [Petville](https://github.com/Majkkrem/Petville) repository-t:  
git bash-ben/ vagy powershell-ben: ```git clone https://github.com/Majkkrem/Petville```

## Létrejön 3 mappa:

### •	PetVille_frontend

  o	Megnyitáskor terminálba ```npm i```
  
    	Majd ```npm i react-bootstrap```
    
    	És ```npm i bootstrap```
    
    	Elindítani ```npm run dev```
    
### •	Petville_Backend

  o	Megnyitás után ```npm i```  
  o Terminálba ```npx prisma db push```  
  o	.env fájl létrehozása:
  
   	DATABASE_URL="mysql://root@localhost:3306/petville"  
   	JWT_SECRET = "ide generáltatni egy kódot" ADMIN_USERNAME = "admin_username" ADMIN_PASSWORD = "admin_password"  
   	Elindítani ```npm run start:dev``` 
    
### •	Petzy -> Java

  o	Ebben semmi további dolog nincsen, csak futtatni a kódot
