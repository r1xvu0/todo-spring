# Todo List API
This project is a simple API for managing a todo list, built with Spring Boot.


### API Endpoints
- `GET /api/todos`
- `POST /api/todos`
- `GET /api/todos/{id}`
- `PUT /api/todos/{id}`
- `DELETE /api/todos/{id}`

### Build the .jar
- Add permission to Maven
- `sudo chmod +x ./mvnw`
- Build the .jar file
- `./mvnw -D package --file pom.xml`
- Launch the app
- `java -jar target/{app}.jar`

### Dockerization
- Build the image
`docker build -t todo-spring:{tag} .`
- Run the image
`docker run -d -p 8080:8080 todo-spring:{tag}`