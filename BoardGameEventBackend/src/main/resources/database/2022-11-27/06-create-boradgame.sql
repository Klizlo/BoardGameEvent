--liquibase formatted sql
--changeset Klizlo:6

create table board_game
(
    id bigint auto_increment primary key,
    name varchar(50) not null,
    min_number_of_players int not null,
    max_number_of_players int not null,
    age_restriction enum ('+7', '+14', '+18') not null,
    created_at timestamp default current_timestamp,
      updated_at datetime default current_timestamp on update current_timestamp,
    board_game_category_id bigint not null,
    producer_id bigint not null,
    constraint boardGame_pk
        unique (name),
    constraint board_game_board_game_category_null_fk
        foreign key (board_game_category_id) references board_game_category(id),
    constraint board_game_producer_null_fk
        foreign key (producer_id) references producer(id)
);