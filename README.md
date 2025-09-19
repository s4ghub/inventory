# Intro

The application is developed to manage a product inventory system

# System used

- OS: Windows 11 home
- IDE: Intellij idea 2025.1.2 community edition
- jdk: 21
- Docker Desktop version: 4.43.1 (198352)

# Assumption

- Logging was not asked in the Task
- From the Product Json in the task requirement, it's assumed that separate individual product unit or serial number is not needed. Only sku is meant here as id (In this case automatically generated).
- It's assumed that the product name is unique


# How to run the application

First Docker desktop should be up and running.
Run the following commands one after another from the terminal. They should be run from inside the project folder.

- mvn clean package -DskipTests
- docker build -t inventory:latest .
- docker-compose up

# How to run the Tests
  
  