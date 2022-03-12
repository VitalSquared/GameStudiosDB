package ru.nsu.spirin.gamestudios.repository.query;

public class TestQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM test;
            """;

    public static final String QUERY_FIND_ALL_WITH_RESULTS =
            """
                SELECT *
                FROM test
                WHERE status_id = 3;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM test
                WHERE test_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO test (test_id, start_date, end_date, grand, min_studios_num, apps_deadline, status_id) VALUES
                 (default, ?, ?, ?, ?, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE test
                SET start_date = ?, end_date = ?, grand = ?, min_studios_num = ?, apps_deadline = ?
                WHERE test_id = ?;
            """;

    public static final String QUERY_UPDATE_TEST_STATUS =
            """
                UPDATE test
                SET status_id = ?
                WHERE test_id = ?;
            """;

    public static final String QUERY_SAVE_TEST_GENRE =
            """
                INSERT INTO test__genre (test_id, genre_id) VALUES
                 (?, ?);
            """;

    public static final String QUERY_DELETE_TEST_GENRE =
            """
                DELETE FROM test__genre
                WHERE test_id = ? AND genre_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_TEST_GENRE =
            """
                DELETE from test__genre
                WHERE test_id = ?;
            """;

    public static final String QUERY_DELETE =
            """
                DELETE FROM test
                WHERE test_id = ?;
            """;
}
