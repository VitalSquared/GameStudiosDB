package ru.nsu.spirin.gamestudios.repository.query;

public class CategoryQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT *
                FROM category
                ORDER BY category_id;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM category cat
                WHERE cat.category_id = ?;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO category (category_id, name) VALUES
                (default, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE category
                SET name = ?
                WHERE category_id = ?;
            """;
}
