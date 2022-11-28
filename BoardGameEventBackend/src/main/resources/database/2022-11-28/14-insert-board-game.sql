--liquibase formatted sql
--changeset Klizlo:14

INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Munchkin', 2, 4, '+7', 1, 1);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Mansion of Madness', 1, 5, '+14', 2, 2);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Talisman', 2, 6, '+14', 2, 2);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Game of Throne', 3, 6, '+14', 4, 2);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Robinson Crusoe', 1, 4, '+14', 4, 4);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Boss Monster', 2, 4, '+7', 1, 6);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Everdell', 2, 4, '+7', 3, 3);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Ticket to ride', 2, 6, '+7', 3, 3);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Nemesis', 2, 4, '+14', 2, 3);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Splendor', 2, 4, '+7', 1, 3);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('Exploding kitten', 2, 5, '+14', 1, 3);
INSERT INTO board_game (name, min_number_of_players, max_number_of_players, age_restriction, board_game_category_id, producer_id)
    values ('5 secund', 3, 6, '+7', 3, 5);