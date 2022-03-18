package ru.nsu.spirin.gamestudios.repository.query;

public class MessageQueries {

    public static final String QUERY_COUNT_TOTAL_SENT_BY_EMAIL =
            """
                SELECT count(1) AS row_count
                FROM (message LEFT JOIN
                        (
                            SELECT received.message_id, array_agg(received.receiver) as receivers, string_agg(received.receiver, ' ') as  receivers_string
                            FROM (message NATURAL JOIN received_message) received
                            GROUP BY received.message_id
                        ) as received1
                     on message.message_id = received1.message_id) msg
                WHERE msg.sender = ? AND %s;
            """;

    public static final String QUERY_FIND_ALL_SENT_BY_EMAIL =
            """
                SELECT *
                FROM (message LEFT JOIN
                        (
                            SELECT received.message_id, array_agg(received.receiver) as receivers, string_agg(received.receiver, ' ') as  receivers_string
                            FROM (message NATURAL JOIN received_message) received
                            GROUP BY received.message_id
                        ) as received1
                     on message.message_id = received1.message_id) msg
                WHERE msg.sender = ? AND %s
                ORDER BY msg.date DESC
                LIMIT ?
                OFFSET ?;
            """;

    public static final String QUERY_FIND_ALL_SENT_BY_EMAIL_SIMPLE =
            """
                SELECT message_id
                FROM message
                WHERE sender = ?;
            """;

    public static final String QUERY_COUNT_TOTAL_RECEIVED_BY_EMAIL =
            """
                WITH received1 AS (
                            SELECT received.message_id, array_agg(received.receiver) as receivers
                            FROM (message NATURAL JOIN received_message) received
                            GROUP BY received.message_id
                            HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                        ),
                    read AS (
                                SELECT received.message_id, received.read
                                FROM received_message received
                                WHERE received.receiver = ?
                     )
                SELECT count(1) AS row_count
                FROM (message NATURAL JOIN
                        (received1 NATURAL JOIN read) as received2
                    ) msg
                WHERE %s;
            """;

    public static final String QUERY_FIND_ALL_RECEIVED_BY_EMAIL =
            """
                WITH received1 AS (
                        SELECT received.message_id, array_agg(received.receiver) as receivers
                        FROM (message NATURAL JOIN received_message) received
                        GROUP BY received.message_id
                        HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                    ),
                    read AS (
                        SELECT received.message_id, received.read
                        FROM received_message received
                        WHERE received.receiver = ?
                 )
                SELECT *
                FROM (message NATURAL JOIN
                        (received1 NATURAL JOIN read) as received2
                    ) msg
                WHERE %s
                ORDER BY msg.date DESC
                LIMIT ?
                OFFSET ?;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT *
                FROM message LEFT JOIN
                        (
                            SELECT received.message_id, array_agg(received.receiver) as receivers
                            FROM (message NATURAL JOIN received_message) received
                            GROUP BY received.message_id
                        ) as received1
                     on message.message_id = received1.message_id
                WHERE message.message_id = ?;
            """;

    public static final String QUERY_FIND_ALL_IDS_BY_EMAIL =
            """
                SELECT msg.message_id
                FROM message msg
                WHERE msg.message_id = ? AND msg.sender = ?
                UNION
                SELECT received.message_id as receivers
                FROM (message NATURAL JOIN received_message) received
                WHERE received.message_id = ? AND received.receiver = ?;
            """;

    public static final String QUERY_COUNT_RECEIVED =
            """
                WITH received1 AS (
                            SELECT received.message_id, array_agg(received.receiver) as receivers
                            FROM (message NATURAL JOIN received_message) received
                            GROUP BY received.message_id
                            HAVING array_agg(received.receiver) @> ARRAY [?::varchar]
                        ),
                     read AS (
                            SELECT received.message_id, received.read
                            FROM received_message received
                            WHERE received.receiver = ?
                     )
                SELECT count(1) AS row_count
                FROM (message NATURAL JOIN
                        (received1 NATURAL JOIN read) as received2
                    ) msg
                WHERE msg.read = ?;
            """;

    public static final String QUERY_COUNT_MAX_ID =
            """
                SELECT MAX(message_id) as max_id
                FROM message;
            """;

    public static final String QUERY_SAVE_SENT_MESSAGE =
            """
                INSERT INTO message (message_id, date, topic, content, sender, attachments) VALUES
                 (default, now(), ?, ?, ?, ?)
                 RETURNING message_id;
            """;

    public static final String QUERY_SAVE_RECEIVED_MESSAGE =
            """
                INSERT INTO received_message (message_id, receiver, read) VALUES
                (?, ?, false);
            """;

    public static final String QUERY_UPDATE_RECEIVED_MESSAGE =
            """
                UPDATE received_message
                SET read = ?
                WHERE message_id = ? AND receiver = ?;
            """;

    public static final String QUERY_DELETE_RECEIVED_MESSAGE =
            """
                DELETE FROM received_message
                WHERE message_id = ? AND receiver = ?;
            """;

    public static final String QUERY_DELETE_ALL_RECEIVED_BY_ID =
            """
                DELETE FROM received_message
                WHERE message_id = ?;
            """;

    public static final String QUERY_DELETE_SENT_MESSAGE =
            """
                DELETE FROM message
                WHERE message_id = ?;
            """;

    public static final String QUERY_DELETE_ALL_SENT_BY_ACCOUNT =
            """
                DELETE FROM message
                WHERE sender = ?;
            """;

    public static final String QUERY_DELETE_ALL_RECEIVED_BY_ACCOUNT =
            """
                DELETE FROM received_message
                WHERE receiver = ?
            """;
}
