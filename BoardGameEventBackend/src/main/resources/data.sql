INSERT INTO role (name, createdAt, updatedAt) values ('ADMIN', '2022-11-25', '2022-11-25');
INSERT INTO role (name, createdAt, updatedAt) values ('USER', '2022-11-25', '2022-11-25');

INSERT INTO users (username, email, password, createdAt, updatedAt) values ('Admin', 'admin@admin.pl', '$2a$12$NwZORfS2lBviTrBRvnm1quWfDTcgZ042CmhxvW.bmJ4P7vhhuBc9W', '2022-11-25', '2022-11-25');

INSERT INTO user_roles (user_id, role_id) values (1, 1);
INSERT INTO user_roles (user_id, role_id) values (1, 2);