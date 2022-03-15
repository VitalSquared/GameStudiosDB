package ru.nsu.spirin.gamestudios.repository.query;

public class AccountQueries {
    public static final String QUERY_FIND_BY_ID =
            """
                SELECT acc.email, acc.passwd_hash, acc.employee_id, acc.active
                FROM (account NATURAL JOIN employee) acc
                WHERE acc.email = ?;
            """;

    public static final String QUERY_FIND_BY_EMPLOYEE_ID =
            """
                SELECT acc.email, acc.passwd_hash, acc.employee_id, acc.active
                FROM (account NATURAL JOIN employee) acc
                WHERE acc.employee_id = ?;
            """;

    public static final String QUERY_FIND_ALL =
            """
                SELECT acc.email, acc.passwd_hash, acc.employee_id, acc.active
                FROM (account NATURAL JOIN employee) acc;
            """;

    public static final String QUERY_SAVE =
            """
                INSERT INTO account (email, passwd_hash, employee_id) VALUES
                (?, ?, ?);
            """;

    public static final String QUERY_UPDATE =
            """
                UPDATE account
                SET email = ?, passwd_hash = ?
                WHERE email = ?;
            """;

    public static final String QUERY_DELETE =
            """
                DELETE FROM account
                WHERE email = ?;
            """;

    public static final String QUERY_DELETE_BY_EMPLOYEE_ID =
            """
                DELETE FROM account
                WHERE employee_id = ?;
            """;;
}
