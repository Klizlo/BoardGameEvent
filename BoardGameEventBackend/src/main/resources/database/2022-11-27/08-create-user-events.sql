--liquibase formatted sql
--changeset Klizlo:8

create table user_events
(
    user_id bigint not null,
    event_id bigint not null,
    constraint user_events_event_null_fk
        foreign key (event_id) references `event` (id),
    constraint user_events_users_null_fk
        foreign key (user_id) references users (id)
);