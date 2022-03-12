package ru.nsu.spirin.gamestudios.repository.query;

public class GameQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM game;
            """;

    public static final String QUERY_FIND_ALL_BY_CONTRACT_ID =
            """
                SELECT game.game_id, game.start_date, game.studio_id, game.dev_name, game.release_name, game.expenses
                FROM game NATURAL JOIN contract__game
                WHERE contract_id = ?;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM game
                WHERE game_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO game (game_id, start_date, dev_name, release_name, expenses, studio_id) VALUES
                 (default, ?, ?, ?, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE game
                SET start_date = ?, dev_name = ?, release_name = ?, expenses = ?
                WHERE game_id = ?;
            """;

    public static final String QUERY_SAVE_GAME_GENRE =
            """
                INSERT INTO game__genre (game_id, genre_id) VALUES
                 (?, ?);
            """;

    public static final String QUERY_DELETE_GAME_GENRE =
            """
                DELETE FROM game__genre
                WHERE game_id = ? AND genre_id = ?;
            """;

    public static final String QUERY_SAVE_GAME_EMPLOYEE =
            """
                INSERT INTO game__employee (game_id, employee_id) VALUES
                 (?, ?);
            """;

    public static final String QUERY_DELETE_GAME_EMPLOYEE =
            """
                DELETE FROM game__employee
                WHERE game_id = ? AND employee_id = ?;
            """;
}
