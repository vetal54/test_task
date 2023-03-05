CREATE TABLE IF NOT EXISTS task
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(20)  NOT NULL,
    description VARCHAR(100) NOT NULL,
    create_date        TIMESTAMP    NOT NULL,
    task_index  INTEGER      NOT NULL,
    column_id   UUID REFERENCES "column_table" ON DELETE CASCADE
);