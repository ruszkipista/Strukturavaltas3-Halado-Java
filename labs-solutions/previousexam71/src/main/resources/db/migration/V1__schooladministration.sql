CREATE TABLE schools
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    name        VARCHAR(255)          NULL,
    postal_code VARCHAR(255)          NULL,
    city        VARCHAR(255)          NULL,
    street      VARCHAR(255)          NULL,
    house_nr    INT                   NULL,
    CONSTRAINT pk_school PRIMARY KEY (id)
);

CREATE TABLE students
(
    id                BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NULL,
    date_of_birth     date                  NULL,
    school_age_status VARCHAR(255)          NULL,
    school_id         BIGINT                NULL,
    CONSTRAINT pk_students PRIMARY KEY (id),
    FOREIGN KEY (school_id) REFERENCES schools (id)
);
