CREATE TABLE types
(
    id          SERIAL PRIMARY KEY,
    type_name   TEXT NOT NULL UNIQUE
);