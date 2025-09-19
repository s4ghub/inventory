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
- The app takes care of concurrent user requests


# How to run the application

Precondition: Docker desktop should be up and running.
Run the following commands one after another from the terminal. They should be run from inside the project folder.

- mvn clean package -DskipTests
- docker build -t inventory:latest .
- docker-compose up

In my case C:\git_new_repo_inventory\inventory>

# For Table creation and DB management: 

### Necessary url: 
http://localhost:8880/browser/

user: admin@admin.com

password: admin
### Register connection to db 
Use the default server group "servers". Right click

Choose "Connection" tab

Host name: db
Port: 5432
Username: user
Password: user
### Choose Database "inventory"

### Create the table with the below query

CREATE TABLE product (
id serial PRIMARY KEY,
name VARCHAR(30) unique NOT NULL,
quantity bigint NOT NULL,
price real NOT NULL,
version bigint NOT NULL
);

# How to invoke the rest endpoints

Necessary url: 

http://localhost:6868/swagger-ui/index.html
  
  