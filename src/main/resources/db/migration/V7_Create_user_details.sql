CREATE TABLE usersdetails (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    phone VARCHAR(50) NOT NULL,
    qualification VARCHAR(255),
    experienced VARCHAR(255),
    organizationworked VARCHAR(255),
    resume VARCHAR(255),
    created_at TIMESTAMP
);