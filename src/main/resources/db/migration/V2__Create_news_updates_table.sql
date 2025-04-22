CREATE TABLE news_updates (
    id SERIAL PRIMARY KEY,
    news VARCHAR(255) NOT NULL,
    content VARCHAR(1000),
    imageurl VARCHAR(255),
    createdat TIMESTAMP
);
