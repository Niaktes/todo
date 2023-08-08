CREATE TABLE IF NOT EXISTS priorities (
    id          serial  PRIMARY KEY,
    name        text    UNIQUE  NOT NULL,
    position    int
);