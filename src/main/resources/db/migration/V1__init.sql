CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(255) NOT NULL UNIQUE,
    email      VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    status     VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scripts
(
    id               SERIAL PRIMARY KEY,
    title            VARCHAR(255)                        NOT NULL,
    description      TEXT,
    file_url         VARCHAR(255),
    downloadable     BOOLEAN   DEFAULT FALSE,
    status           VARCHAR(50),
    rejection_reason TEXT,
    user_id          BIGINT,
    created_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at       TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);
