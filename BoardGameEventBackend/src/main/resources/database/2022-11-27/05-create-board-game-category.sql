--liquibase formatted sql
--changeset Klizlo:5

create table board_game_category
(
    id bigint auto_increment primary key,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,
    constraint boardGameCategory_pk
        unique (name)
);