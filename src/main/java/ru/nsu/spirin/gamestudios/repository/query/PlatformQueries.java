package ru.nsu.spirin.gamestudios.repository.query;

public class PlatformQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM platform
                ORDER BY platform_id;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM platform p
                WHERE p.platform_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO platform (platform_id, name, percent) VALUES
                (default, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE platform
                SET name = ?, percent = ?
                WHERE platform_id = ?;
            """;
}
