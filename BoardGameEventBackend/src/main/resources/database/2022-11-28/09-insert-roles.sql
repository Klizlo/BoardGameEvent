--liquibase formatted sql
--changeset Klizlo:9

INSERT INTO role (name) values ('ADMIN');
INSERT INTO role (name) values ('USER');