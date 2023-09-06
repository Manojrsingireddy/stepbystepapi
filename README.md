## StepByStep Application Backend

### Description
Welcome to the backend repository of our Health and Well-Being Application. This repository houses the Java code for the Spring Boot application that powers the logic layer of our application. Below, you'll find information on how the backend is structured, the technologies used, and key considerations.

This backend repository is an integral part of our Health and Well-Being Application. It provides the logic and functionality required to manage user authentication, health goal generation, and quiz interactions. The backend is built using Java 17 and the Spring Boot framework, known for its microservices architecture.

### Technologies Used
- Java 17: The core programming language for building the backend logic.
- Spring Boot: A powerful framework for building robust and scalable APIs.
- MongoDB: A NoSQL database used to store user data, quizzes, and health goals.
- AWS Lambda: For deploying the backend application in a serverless environment.
- Amazon API Gateway: Used for managing and exposing the API endpoints.
- OpenAI GPT-3.5: Integrated for generating personalized health goals based on user information.

### Project Structure
The project is structured around three main components:

- Controller Layer: This layer defines the application's endpoints, handling user authentication, quiz interactions, and goal management. The key classes here are UserController, QuizController, and GoalController.

- Service Layer: The service layer contains the business logic for the application. It handles interactions between the controllers and the data layer. You can find service classes for each entity (user, quiz, goal).

- Repository Layer: The repository layer interacts directly with the MongoDB database, implementing data modifications and retrieval. Each entity (user, quiz, goal) has its repository class.

### Getting Started
To run the backend locally for development or testing purposes, follow these steps:

Clone this repository to your local machine.
Make sure you have Java 17 and Maven installed.
Set up a MongoDB instance and configure the connection in the .env file as shown in .env.example.
Configure your OpenAI GPT-3.5 API key.
Build and run the project by running the StepByStepApplication java file.

### Deployment
The backend of our Health and Well-Being Application is designed to be deployed on AWS Lambda. The deployment process includes packaging the application into a JAR file and configuring AWS Lambda and API Gateway. The code contains a Lambda stream handler to support asynchronous application launch.