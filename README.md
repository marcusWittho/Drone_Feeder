# Projeto Drone Feeder

 Desenvolvimento de uma api para controlar entregas que serão realizadas por drones.
 Ela será consumida por um front-end que deverá apresentar informações sobre a entrega e o percurso que o drone está fazendo.
 
## Iniciando o projeto.
 Após fazer o clone do projeto seguir os seguintes passos.

 - Acessar a pasta raiz do projeto


 - Subir o mysql com o docker
 ```
    docker-compose up -d
 ```

 - Criar um banco chamado drone via terminal
 ```
    // Executa o mysql criado a partir do docker
    docker exec -it nome-do-container mysql -u root -p
    
    // Quando estiver dentro do mysql, basta criar o banco
    create database drone;
    
    // Sair do mysql e voltar ao terminal
    exit
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