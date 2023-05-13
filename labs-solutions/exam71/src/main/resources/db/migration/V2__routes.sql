CREATE TABLE routes (
    id                BIGINT AUTO_INCREMENT NOT NULL,
    airplane_id       BIGINT                NULL,
    departure_city    VARCHAR(255)          NOT NULL,
    arrival_city      VARCHAR(255)          NOT NULL, 
    date_of_flight    VARCHAR(255)          NOT NULL,
    CONSTRAINT pk_routes PRIMARY KEY (id),
    FOREIGN KEY (airplane_id) REFERENCES airplanes (id)
);

