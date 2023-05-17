CREATE TABLE tests (
    id                BIGINT AUTO_INCREMENT NOT NULL,
    subject           VARCHAR(255)          NOT NULL,
    test_value        VARCHAR(30)           NOT NULL,
    student_id        BIGINT                NULL,
    CONSTRAINT pk_tests PRIMARY KEY (id),
    FOREIGN KEY (student_id) REFERENCES students (id)
);
