CREATE TABLE airplanes (
    id            BIGINT AUTO_INCREMENT NOT NULL,
    airplane_type VARCHAR(255)          NULL,
    owner_airline VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_airplane PRIMARY KEY (id)
);
