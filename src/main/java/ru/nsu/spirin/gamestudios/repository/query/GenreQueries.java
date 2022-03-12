package ru.nsu.spirin.gamestudios.repository.query;

public class GenreQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM genre
                ORDER BY genre_id
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM genre
                WHERE genre_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO genre (genre_id, name) VALUES
                (default, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE genre
                SET name = ?
                WHERE genre_id = ?;
            """;
}
