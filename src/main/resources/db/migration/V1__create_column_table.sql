CREATE TABLE IF NOT EXISTS column_table
(
    id           SERIAL PRIMARY KEY,
    name         VARCHAR(20) NOT NULL,
    column_index INTEGER     NOT NULL
);