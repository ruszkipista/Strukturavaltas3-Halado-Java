create table members (id bigint not null auto_increment, enabled bit not null, name varchar(255), primary key (id));
create table questions (id bigint not null auto_increment, answer_text varchar(255), answered bit not null, answered_at datetime(6), created_at datetime(6), question_text varchar(255), votes integer not null, member_id bigint, primary key (id));
alter table questions add constraint FK1nuuke7olg7b9fkyp2ba9d5bx foreign key (member_id) references members (id);
