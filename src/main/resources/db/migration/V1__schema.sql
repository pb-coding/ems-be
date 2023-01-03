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
DROP TABLE IF EXISTS refresh_token_entity;

CREATE TABLE refresh_token_entity (
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    refresh_token VARCHAR(64) NOT NULL,
    revoked BOOLEAN NOT NULL,
    date_created DATE NOT NULL
);