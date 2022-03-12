package ru.nsu.spirin.gamestudios.repository.query;

public class DepartmentQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM department;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM department
                WHERE department_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO department (department_id, studio_id, name, head_id, is_root) VALUES
                (default, ?, ?, ?, false);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE department
                SET name = ?, head_id = ?
                WHERE department_id = ?;
            """;

    public static final String QUERY_FIND_ALL_BY_STUDIO_ID =
            """
                SELECT *
                FROM department
                WHERE studio_id = ?;
            """;
}
