package ru.nsu.spirin.gamestudios.repository.query;

public class TestAppResultQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM test_app_result;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM test_app_result
                WHERE result_id = ?;
            """;
}
