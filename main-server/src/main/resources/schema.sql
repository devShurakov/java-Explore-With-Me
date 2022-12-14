DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS events CASCADE;
DROP TABLE IF EXISTS compilations CASCADE;
DROP TABLE IF EXISTS compilation_event CASCADE;

CREATE TABLE IF NOT EXISTS users (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(64) NOT NULL,
    email VARCHAR(256) NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_email UNIQUE (email)
    );

CREATE TABLE IF NOT EXISTS categories (
    category_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    name VARCHAR(32) NOT NULL,
    CONSTRAINT pk_id PRIMARY KEY (category_id),
    CONSTRAINT uq_name UNIQUE (name)
    );

CREATE TABLE IF NOT EXISTS events (
    event_id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    annotation VARCHAR(4000),
    category_id BIGINT,
    confirmed_requests BIGINT,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    description VARCHAR(4000),
    event_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    initiator_id BIGINT,
    location_lat FLOAT,
    location_lon FLOAT,
    paid BOOLEAN,
    participant_limit BIGINT,
    published_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    request_moderation BOOLEAN,
    event_status VARCHAR(32),
    title VARCHAR,
    views BIGINT,
    CONSTRAINT pk_event_id PRIMARY KEY (event_id),
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES categories (category_id),
    CONSTRAINT fk_initiator_id FOREIGN KEY (initiator_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS requests (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    event_id BIGINT,
    requester_id BIGINT,
    requests_status VARCHAR(32),
    CONSTRAINT pk_request_id PRIMARY KEY (id),
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_requester_id FOREIGN KEY (requester_id) REFERENCES users (id)
    );

CREATE TABLE IF NOT EXISTS compilations (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL PRIMARY KEY,
    pinned BOOLEAN,
    title VARCHAR(128)
    );

CREATE TABLE IF NOT EXISTS compilation_event (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    compilation_id BIGINT NOT NULL,
    event_id BIGINT NOT NULL,
    CONSTRAINT pk_compilation_id PRIMARY KEY (id),
    CONSTRAINT fk_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (id) ON DELETE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id) ON DELETE CASCADE,
    CONSTRAINT UQ_COMPILATION_EVENT UNIQUE (compilation_id, event_id)
    );

CREATE TABLE IF NOT EXISTS comments (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    text VARCHAR(4000) NOT NULL,
    event_id BIGINT  NOT NULL,
    author_id BIGINT  NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_comment PRIMARY KEY (id),
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (event_id),
    CONSTRAINT fk_author_id FOREIGN KEY (author_id) REFERENCES users (id)
    );

