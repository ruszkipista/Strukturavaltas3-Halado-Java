create table players (id bigint not null auto_increment, date_of_birth date, name varchar(255), position varchar(255), team_id bigint, primary key (id));

create table teams (id bigint not null auto_increment, name varchar(255), primary key (id));