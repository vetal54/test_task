CREATE TABLE IF NOT EXISTS task
(
    id              SERIAL PRIMARY KEY,
    name            VARCHAR(20) NOT NULL,
    description     VARCHAR(100) NOT NULL,
    create_date     TIMESTAMP NOT NULL,
    task_index      INTEGER NOT NULL,
    column_id       BIGINT REFERENCES "column_table" ON DELETE CASCADE
);