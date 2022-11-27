--liquibase formatted sql
--changeset Klizlo:1

create table role (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) not null,
  createdAt datetime null,
  updatedAt datetime null
);

alter table `role`
    add constraint UC_role_name unique (name);