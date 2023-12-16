create table questions
(
    id      int auto_increment primary key,
    content varchar(128) not null,
    test_id int          null,
    constraint questions_ibfk_1 foreign key (test_id) references tests (id)
);

create index test_id
    on questions (test_id);

