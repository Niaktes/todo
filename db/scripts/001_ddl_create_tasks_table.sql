CREATE TABLE if NOT EXISTS tasks
(
    id          serial      PRIMARY KEY,
    name        text        NOT NULL,
    description text,
    created     timestamp,
    done        boolean
);