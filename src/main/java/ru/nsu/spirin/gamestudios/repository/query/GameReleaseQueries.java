package ru.nsu.spirin.gamestudios.repository.query;

public class GameReleaseQueries {
    public static final String QUERY_FIND_ALL_BY_GAME_ID =
            """
                SELECT *
                FROM game__contract__platform
                WHERE game_id = ?;
            """;

    public static final String QUERY_FIND_BY_GAME_ID_AND_PLATFORM_ID =
            """
                SELECT *
                FROM game__contract__platform
                WHERE game_id = ? AND platform_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO game__contract__platform (game_id, contract_id, platform_id, release_date, cost, sold_count) VALUES
                (?, ?, ?, ?, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE game__contract__platform
                SET release_date = ?, cost = ?, sold_count = ?
                WHERE game_id = ? AND platform_id = ?;
            """;

    public static final String QUERY_DELETE =
            """
                DELETE FROM game__contract__platform
                WHERE game_id = ? AND platform_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_BY_CONTRACT_ID =
            """
                DELETE FROM game__contract__platform
                WHERE contract_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_BY_GAME_ID =
            """
                DELETE FROM game__contract__platform
                WHERE game_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_BY_PLATFORM_ID =
            """
                DELETE FROM game__contract__platform
                WHERE platform_id = ?;
            """;
}
