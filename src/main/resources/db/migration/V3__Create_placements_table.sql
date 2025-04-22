CREATE TABLE placements (
    id SERIAL PRIMARY KEY,
    jobtitle VARCHAR(255) NOT NULL,
    description VARCHAR(1000),
    jobpackage NUMERIC,
    createdat TIMESTAMP
);
