ALTER TABLE tasks
    ADD COLUMN IF NOT EXISTS
    user_id     int     REFERENCES users(id)    NOT NULL;