CREATE TABLE IF NOT EXISTS tasks_categories (
    id              serial  PRIMARY KEY,
    task_id         int     NOT NULL REFERENCES tasks(id),
    category_id     int     NOT NULL REFERENCES categories(id),
    UNIQUE (task_id, category_id)
)