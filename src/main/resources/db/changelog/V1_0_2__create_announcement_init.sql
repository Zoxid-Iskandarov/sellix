CREATE TABLE announcement
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(100)   NOT NULL,
    description TEXT           NOT NULL,
    price       DECIMAL(12, 2) NOT NULL,
    city        VARCHAR(100)   NOT NULL,
    user_id     BIGINT         NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    created_at  TIMESTAMP      NOT NULL,
    updated_at  TIMESTAMP DEFAULT NULL
);

CREATE TABLE announcement_image
(
    id              BIGSERIAL PRIMARY KEY,
    image           VARCHAR(100) NOT NULL,
    announcement_id BIGINT       NOT NULL REFERENCES announcement (id) ON DELETE CASCADE,
    is_preview      BOOLEAN      NOT NULL DEFAULT FALSE,
    created_at      TIMESTAMP    NOT NULL DEFAULT CURRENT_TIMESTAMP,
    created_by      VARCHAR(100) NOT NULL
);