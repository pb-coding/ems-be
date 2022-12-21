DROP TABLE IF EXISTS user_entity;

CREATE TABLE user_entity (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(64) NOT NULL,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    email VARCHAR(50),
    active BOOLEAN NOT NULL,
    creation_date DATE NOT NULL
);