CREATE TABLE students (
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NOT NULL,
    school_name     VARCHAR(255)          NOT NULL,
    city            VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_student PRIMARY KEY (id)
);
