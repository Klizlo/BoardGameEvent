--liquibase formatted sql
--changeset Klizlo:4

create table producer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp,
    constraint UC_producer_name unique (name)
);