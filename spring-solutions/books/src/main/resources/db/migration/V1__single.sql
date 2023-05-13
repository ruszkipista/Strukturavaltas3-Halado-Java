CREATE TABLE singles (
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NOT NULL,
    enumerated_type VARCHAR(30)           NOT NULL,
    CONSTRAINT pk_single PRIMARY KEY (id)
);
