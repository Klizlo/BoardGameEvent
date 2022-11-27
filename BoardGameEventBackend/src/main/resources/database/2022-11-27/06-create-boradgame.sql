--liquibase formatted sql
--changeset Klizlo:6

create table board_game
(
    id bigint auto_increment primary key,
    name varchar(20) not null,
    minNumberOfPlayers int not null,
    maxNumberOfPlayers int not null,
    ageRestriction enum ('+7', '+14', '+18') not null,
    createdAt datetime null,
    updatedAt datetime null,
    board_game_category_id bigint not null,
    producer_id bigint not null,
    constraint boardGame_pk
        unique (name),
    constraint board_game_board_game_category_null_fk
        foreign key (board_game_category_id) references board_game_category(id),
    constraint board_game_producer_null_fk
        foreign key (producer_id) references producer(id)
);