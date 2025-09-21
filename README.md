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

In my case the above commands were run from C:\git_new_repo_inventory\inventory>

# For Table creation and DB management: 

### Necessary url: 
http://localhost:8880/browser/

user: admin@admin.com

password: admin
### Register connection to db 
Use the default server group "servers". Right click over it. Choose Register->Server

Choose "Connection" tab

Host name: db

Port: 5432

Username: user

Password: user

Save connection

### Choose Database "inventory"

Right click over Inventory and choose query tool

### Create the table with the below query

CREATE TABLE product (
id serial PRIMARY KEY,
name VARCHAR(30) unique NOT NULL,
quantity bigint NOT NULL,
price VARCHAR(30) NOT NULL,
version bigint NOT NULL
);

# How to invoke the rest endpoints

Necessary url: 

http://localhost:6868/swagger-ui/index.html

## Example requests

### Create product

curl -X 'POST' \
'http://localhost:6868/products' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"name": "HeadPhone",
"quantity": 100,
"price": "100.90"
}'

### get all products

curl -X 'GET' \
'http://localhost:6868/products?page=0&size=2&sortField=id&direction=ASC' \
-H 'accept: */*'

### Search a product

curl -X 'GET' \
'http://localhost:6868/products/search?name=heADphone' \
-H 'accept: */*'

### Update the quantity

curl -X 'PUT' \
'http://localhost:6868/products/1/quantity' \
-H 'accept: */*' \
-H 'Content-Type: application/json' \
-d '{
"quantity": -20
}'

### Delete a product

curl -X 'DELETE' \
'http://localhost:6868/products/1' \
-H 'accept: */*'

### Get the summary

curl -X 'GET' \
'http://localhost:8085/products/summary' \
-H 'accept: */*'