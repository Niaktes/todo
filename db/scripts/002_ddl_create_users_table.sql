CREATE TABLE if NOT EXISTS users
(
    id          serial      PRIMARY KEY,
    name        text        NOT NULL,
    login       text        NOT NULL UNIQUE,
    password    text        NOT NULL
);