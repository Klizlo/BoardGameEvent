--liquibase formatted sql
--changeset Klizlo:11

INSERT INTO user_roles (user_id, role_id) values (1, 1);
INSERT INTO user_roles (user_id, role_id) values (1, 2);