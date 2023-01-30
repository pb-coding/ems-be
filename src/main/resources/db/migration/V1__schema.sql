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
INSERT INTO user_entity(id, user_name, first_name, last_name, email, active, creation_date)
VALUES (1, 'pb1497', 'Philipp', 'Beckmann', 'pb1497@googlemail.com', true, '2023-01-01');

DROP TABLE IF EXISTS refresh_token_entity;

CREATE TABLE refresh_token_entity (
    id SERIAL PRIMARY KEY,
    username VARCHAR(64) NOT NULL,
    refresh_token VARCHAR(64) NOT NULL,
    revoked BOOLEAN NOT NULL,
    date_created DATE NOT NULL
);
DROP TABLE IF EXISTS enphase_auth_entity;

CREATE TABLE enphase_auth_entity (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    access_token VARCHAR NOT NULL,
    refresh_token VARCHAR NOT NULL,
    creation_date DATE NOT NULL,
    last_updated DATE NOT NULL
);