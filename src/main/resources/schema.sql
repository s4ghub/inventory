DROP TABLE IF EXISTS product;

CREATE TABLE product ( id serial PRIMARY KEY, name VARCHAR(30) unique NOT NULL, quantity bigint NOT NULL, price real NOT NULL, version bigint NOT NULL );