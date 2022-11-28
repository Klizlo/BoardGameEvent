--liquibase formatted sql
--changeset Klizlo:1

create table role (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) not null,
    created_at timestamp default current_timestamp,
    updated_at datetime default current_timestamp on update current_timestamp
);

alter table `role`
    add constraint UC_role_name unique (name);