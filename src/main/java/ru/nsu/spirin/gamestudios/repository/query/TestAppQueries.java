package ru.nsu.spirin.gamestudios.repository.query;

public class TestAppQueries {
    public static final String QUERY_FIND_ALL_BY_GAME_ID =
            """
                SELECT *
                FROM test_app
                WHERE test_id = ?;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM test_app
                WHERE app_id = ?;
            """;

    public static final String QUERY_UPDATE_RESULT =
            """
                UPDATE test_app
                SET result_id = ?
                WHERE app_id = ?;
            """;

    public static final String QUERY_SAVE_APP_EMPLOYEE =
            """
                INSERT INTO test_app__employee (app_id, employee_id) VALUES
                (?, ?);
            """;

    public static final String QUERY_DELETE_APP_EMPLOYEE =
            """
                DELETE FROM test_app__employee
                WHERE app_id = ? AND employee_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_APP_EMPLOYEE =
            """
                DELETE FROM test_app__employee
                WHERE app_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_APP_EMPLOYEE_BY_EMPLOYEE_ID =
            """
                DELETE FROM test_app__employee
                WHERE employee_id = ?;
            """;

    public static final String QUERY_DELETE_PENDING_APPS =
            """
                DELETE FROM test_app
                WHERE test_id = ? AND result_id = 0;
            """;

    public static final String QUERY_DELETE_APP =
            """
                DELETE from test_app
                WHERE app_id = ?;
            """;
}
