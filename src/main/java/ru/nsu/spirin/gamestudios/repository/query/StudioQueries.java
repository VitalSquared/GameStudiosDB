package ru.nsu.spirin.gamestudios.repository.query;

public class StudioQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM studio st
                ORDER BY st.studio_id;
            """;

    public static final String QUERY_FIND_ALL_BY_ID =
            """
                SELECT *
                FROM studio st
                WHERE %s
                ORDER BY st.studio_id;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM studio st
                WHERE st.studio_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO studio (studio_id, name, address) VALUES
                 (default, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE studio
                SET name = ?, address = ?
                WHERE studio_id = ?;
            """;

    public static final String QUERY_DELETE =
            """
                DELETE FROM studio
                WHERE studio_id = ?;
            """;
}
