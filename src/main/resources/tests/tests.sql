create table tests
(
    id         int auto_increment primary key,
    name       varchar(64)       not null,
    is_deleted tinyint default 0 null
);

