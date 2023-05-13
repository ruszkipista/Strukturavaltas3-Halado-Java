create table world_record (id bigint not null auto_increment, date_of_record date, description varchar(255), unit_of_measure varchar(255),
value double precision, recorder_id bigint, primary key (id));

alter table world_record add constraint word_record_fk foreign key (recorder_id) references recorder (id);