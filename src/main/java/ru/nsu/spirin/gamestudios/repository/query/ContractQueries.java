package ru.nsu.spirin.gamestudios.repository.query;

public class ContractQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM contract;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM contract
                WHERE contract_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO contract (contract_id, date, percent, test_id) VALUES
                (default, ?, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE contract
                SET date = ?, percent = ?
                WHERE contract_id = ?;
            """;

    public static final String QUERY_DELETE =
            """
                DELETE FROM contract
                WHERE contract_id = ?;
            """;

    public static final String QUERY_FIND_ALL_BY_GAME_ID =
            """
                SELECT c.contract_id, c.percent, c.date, c.test_id
                FROM (contract NATURAL JOIN contract__game) c
                WHERE c.game_id = ?;
            """;

    public static final String QUERY_SAVE_GAME_CONTRACT =
            """
                INSERT INTO contract__game (game_id, contract_id) VALUES
                (?, ?);
            """;

    public static final String QUERY_DELETE_GAME_CONTRACT =
            """
                DELETE FROM contract__game
                WHERE game_id = ? AND contract_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_GAME_CONTRACT =
            """
                DELETE FROM contract__game
                WHERE contract_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_GAME_CONTRACT_BY_GAME_ID =
            """
                DELETE FROM contract__game
                WHERE game_id = ?;
            """;
}
