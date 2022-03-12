package ru.nsu.spirin.gamestudios.repository.query;

public class TestStatusQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM test_status;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM test_status
                WHERE status_id = ?;
            """;
}
