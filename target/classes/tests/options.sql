create table options
(
    id          int auto_increment primary key,
    content     varchar(128)      not null,
    question_id int               null,
    is_correct  tinyint default 0 null,
    constraint options_ibfk_1 foreign key (question_id) references questions (id)
);

create index question_id on options (question_id);

