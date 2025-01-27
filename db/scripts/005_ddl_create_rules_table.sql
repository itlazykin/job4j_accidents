CREATE TABLE rules
(
        id              SERIAL PRIMARY KEY,
        rule_name       TEXT NOT NULL UNIQUE
);