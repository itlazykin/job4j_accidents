CREATE TABLE accidents
(
  id        SERIAL PRIMARY KEY,
  name      TEXT,
  text      TEXT NOT NULL,
  address   TEXT NOT NULL,
  type_id   INT REFERENCES types(id)
);