CREATE TABLE multiples (
    id                BIGINT AUTO_INCREMENT NOT NULL,
    name              VARCHAR(255)          NOT NULL,
    single_id         BIGINT                NULL,
    CONSTRAINT pk_multiples PRIMARY KEY (id),
    FOREIGN KEY (single_id) REFERENCES singles (id)
);

-- ALTER TABLE IF EXISTS single 
--   ADD multiple_id bigint;

-- ALTER TABLE IF EXISTS multiples
--   ADD CONSTRAINT fk_single FOREIGN KEY (single_id) REFERENCES singles (id);

