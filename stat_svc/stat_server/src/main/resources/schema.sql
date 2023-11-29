DROP TABLE IF EXISTS stats;
CREATE TABLE IF NOT EXISTS stats (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    app	VARCHAR NOT NULL,
    uri	VARCHAR NOT NULL,
    ip	VARCHAR NOT NULL,
    created TIMESTAMP NOT NULL
);