CREATE TABLE users
(
    id           BIGSERIAL PRIMARY KEY,
    username     VARCHAR(100) NOT NULL UNIQUE,
    first_name   VARCHAR(100) NOT NULL,
    last_name    VARCHAR(100) NOT NULL,
    password     VARCHAR(100) NOT NULL,
    phone_number VARCHAR(20),
    role         VARCHAR(10)  NOT NULL CHECK (role IN ('USER', 'ADMIN')),
    avatar       VARCHAR(100),
    status       BOOLEAN DEFAULT TRUE,
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    DEFAULT NULL
)