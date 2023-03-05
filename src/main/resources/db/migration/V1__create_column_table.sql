CREATE TABLE IF NOT EXISTS column_table
(
    id           UUID PRIMARY KEY,
    name         VARCHAR(20) NOT NULL,
    column_index INTEGER     NOT NULL
);