CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    createdat TIMESTAMP,
    role VARCHAR(255) NOT NULL
);
