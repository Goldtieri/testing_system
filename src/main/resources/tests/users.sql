create table users
(
    id       int auto_increment primary key,
    login    varchar(32)            not null,
    password varchar(32)            not null,
    is_admin tinyint(1) default (0) null
);

