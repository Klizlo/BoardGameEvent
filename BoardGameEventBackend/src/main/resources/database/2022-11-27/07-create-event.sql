--liquibase formatted sql
--changeset Klizlo:7

create table `event`
(
    id bigint auto_increment primary key,
    name varchar(20) not null,
    numberOfPlayers int not null,
    description text null,
    createdAt datetime null,
    updatedAt datetime null,
    board_game_id bigint not null,
    organizer_id bigint not null,
    date datetime(6) not null,
    constraint gameEvent_pk
        unique (name),
    constraint event_board_game_null_fk
        foreign key (board_game_id) references board_game (id),
    constraint event_users_null_fk
        foreign key (organizer_id) references users (id)
);