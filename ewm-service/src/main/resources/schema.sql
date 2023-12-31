DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS location CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS events_compilation CASCADE;
DROP TABLE If EXISTS requests CASCADE;

CREATE TABLE IF NOT EXISTS users
(
    id    BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR(250) UNIQUE                     NOT NULL,
    email VARCHAR(254) UNIQUE                     NOT NULL
);

CREATE TABLE IF NOT EXISTS categories
(
    id   BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR(50) UNIQUE                      NOT NULL
);

CREATE TABLE IF NOT EXISTS location
(
    id  BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    lat FLOAT,
    lon FLOAT
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    annotation         VARCHAR(2000)                           NOT NULL,
    category_id        BIGINT                                  NOT NULL REFERENCES categories (id),
    confirmed_requests BIGINT,
    created_on         TIMESTAMP WITHOUT TIME ZONE,
    description        VARCHAR(7000),
    event_date         TIMESTAMP WITHOUT TIME ZONE             NOT NULL,
    initiator_id       BIGINT                                  NOT NULL REFERENCES users (id),
    location_id        BIGINT REFERENCES location (id),
    paid               BOOLEAN,
    participant_limit  INTEGER DEFAULT 0,
    published_date     TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN DEFAULT true,
    state              VARCHAR(20),
    title              VARCHAR(120)                            NOT NULL
);

CREATE TABLE IF NOT EXISTS requests
(
    id           BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    event_id     BIGINT                                  NOT NULL REFERENCES events (id),
    requester_id BIGINT                                  NOT NULL REFERENCES users (id),
    created      TIMESTAMP WITHOUT TIME ZONE,
    status       VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id     BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    pinned BOOLEAN                                 NOT NULL,
    title  VARCHAR(50)                             NOT NULL
);

CREATE TABLE IF NOT EXISTS events_compilation
(
    event_id       BIGINT NOT NULL REFERENCES events (id) ON UPDATE CASCADE,
    compilation_id BIGINT NOT NULL REFERENCES compilations (id) ON UPDATE CASCADE,
    PRIMARY KEY (event_id, compilation_id)
);