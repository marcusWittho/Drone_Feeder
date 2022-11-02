# Projeto Drone Feeder

 Desenvolvimento de uma api para controlar entregas que serão realizadas por drones.
 Ela será consumida por um front-end que deverá apresentar informações sobre a entrega e o percurso que o drone está fazendo.
 
## Iniciando o projeto.
 Após fazer o clone do projeto seguir os seguintes passos.

 - Acessar a pasta raiz do projeto


 - Subir o mysql com o docker e criar o banco chamado drone.
 ```
    docker-compose up -d
 ```

 - Subir a aplicação
 ```
    ./gradlew bootRun
 ```

 A partir deste momento já pode utilizar o insomnia ou postman para testar os endpoints.
 
 - Endpoint para adicionar um novo drone
 ```
   // POST - endpoint para adicionar drone
   http://localhost:8080/drone/add
   
   // exemplo de body   
   {
	"serialNumber": "a100",
	"latitude": -46.761107,
	"longitude": -23.5747372,
	"operando": true
   }
   
   // GET - endpoint para buscar por id
   http://localhost:8080/drone/[número-do-id]
 ```

 - Os testes podem ser executados com o seguinte comando
 ```
    ./gradlew test
 ```