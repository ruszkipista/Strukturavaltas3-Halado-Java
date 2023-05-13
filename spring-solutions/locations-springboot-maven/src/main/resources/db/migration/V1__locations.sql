CREATE TABLE locations (
    id            bigint AUTO_INCREMENT,
    location_name varchar(255),
    latitude      double,
    longitude     double,
    primary key (id)
);