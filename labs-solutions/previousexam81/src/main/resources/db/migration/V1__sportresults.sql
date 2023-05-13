CREATE TABLE athletes
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    name VARCHAR(255)          NULL,
    sex  VARCHAR(255)          NULL,
    CONSTRAINT pk_athletes PRIMARY KEY (id)
);
CREATE TABLE results
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    place        VARCHAR(255)          NULL,
    result_date  date                  NULL,
    sport_type   VARCHAR(255)          NULL,
    measure      DOUBLE                NOT NULL,
    measure_unit CHAR                  NOT NULL,
    athlete_id   BIGINT                NULL,
    CONSTRAINT pk_results PRIMARY KEY (id)
);

ALTER TABLE results
    ADD CONSTRAINT FK_RESULTS_ON_ATHLETE FOREIGN KEY (athlete_id) REFERENCES athletes (id);