--liquibase formatted sql
--changeset Klizlo:3

create table user_roles (
  user_id bigint,
  role_id bigint
);

Alter table `user_roles`
  add constraint user_role_id
  foreign key (role_id) references `role`(id);

Alter table `user_roles`
  add constraint role_user_id
  foreign key (user_id) references `users`(id);