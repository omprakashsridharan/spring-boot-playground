CREATE TABLE IF NOT EXISTS book
(
    id
    SERIAL
    PRIMARY
    KEY,
    title
    VARCHAR
(
    255
) NOT NULL,
    isbn VARCHAR
(
    20
) NOT NULL
    );