--liquibase formatted sql
--changeset Klizlo:5

create table board_game_category
(
    id bigint auto_increment primary key,
    name varchar(255) not null,
    createdAt datetime null,
    updatedAt datetime null,
    constraint boardGameCategory_pk
        unique (name)
);