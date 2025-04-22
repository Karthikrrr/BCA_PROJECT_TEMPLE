CREATE TABLE projects (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    content VARCHAR(1000),
    imageurl VARCHAR(255),
    createdat TIMESTAMP
);
