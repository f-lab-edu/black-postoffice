DROP TABLE IF EXISTS USERS;

CREATE TABLE USERS (
    id INTEGER (10) PRIMARY KEY,
    name VARCHAR(20) NOT NULL,
    password VARCHAR(10) NOT NULL
);