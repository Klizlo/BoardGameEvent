--liquibase formatted sql
--changeset Klizlo:10

INSERT INTO users (username, email, password) values ('Admin', 'admin@admin.pl', '$2a$12$NwZORfS2lBviTrBRvnm1quWfDTcgZ042CmhxvW.bmJ4P7vhhuBc9W');