--liquibase formatted sql
--changeset Klizlo:4

create table producer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name varchar(255) not null,
  createdAt datetime null,
  updatedAt datetime null,
  constraint UC_producer_name unique (name)
);