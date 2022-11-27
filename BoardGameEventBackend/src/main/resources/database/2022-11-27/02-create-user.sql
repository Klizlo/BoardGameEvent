--liquibase formatted sql
--changeset Klizlo:2

create table users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  username varchar(16) not null,
  email varchar(255) not null,
  password varchar(255) not null,
  createdAt datetime null,
  updatedAt datetime null,
  constraint UC_user_name unique (username),
  constraint UC_user_email unique (email)
);