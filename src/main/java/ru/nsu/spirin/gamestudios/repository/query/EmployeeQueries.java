package ru.nsu.spirin.gamestudios.repository.query;

public class EmployeeQueries {
    public static final String QUERY_FIND_ALL =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                            dev.category_id, dev.department_id, e.active,
                            case when (dir.studio_id is not null) then
                                dir.studio_id
                            else
                                dep.studio_id
                            end as studio_id
                FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                             LEFT JOIN (developer dev NATURAL JOIN department dep)
                             on e.employee_id = dev.employee_id
                WHERE e.employee_id != 0;
            """;

    public static final String QUERY_COUNT_DEVELOPERS_BY_ID =
            """
                SELECT count(*)
                FROM developer dev
                WHERE dev.employee_id = ?;
            """;

    public static final String QUERY_FIND_DIRECTOR_STUDIO_BY_ID =
            """
                SELECT dir.studio_id
                FROM director dir
                WHERE dir.employee_id = ?;
            """;

    public static final String QUERY_FIND_BY_ID =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                        dev.category_id, dev.department_id, e.active,
                        case when (dir.studio_id is not null) then
                            dir.studio_id
                        else
                            dep.studio_id
                        end as studio_id
                FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                         LEFT JOIN (developer dev NATURAL JOIN department dep)
                         on e.employee_id = dev.employee_id
                WHERE e.employee_id = ?;
            """;

    public static final String QUERY_SAVE_EMPLOYEE =
            """
                INSERT INTO employee (employee_id, first_name, last_name, birth_date, active) VALUES
                 (default, ?, ?, ?, ?)
                 RETURNING employee_id;
            """;

    public static final String QUERY_SAVE_DEVELOPER =
            """
                INSERT INTO developer (employee_id, category_id, department_id) VALUES
                (?, ?, ?);
            """;

    public static final String QUERY_SAVE_DIRECTOR =
            """
                INSERT INTO director (employee_id, studio_id) VALUES
                (?, ?);
            """;

    public static final String QUERY_UPDATE_EMPLOYEE =
            """
                UPDATE employee
                SET first_name = ?, last_name = ?, birth_date = ?, active = ?
                WHERE employee_id = ?;
            """;

    public static final String QUERY_UPSERT_DEVELOPER =
            """
                INSERT INTO developer (employee_id, category_id, department_id) VALUES
                (?, ?, ?)
                ON CONFLICT (employee_id) DO UPDATE
                SET category_id = ?, department_id = ?
            """;

    public static final String QUERY_UPSERT_DIRECTOR =
            """
                INSERT INTO director (employee_id, studio_id) VALUES
                (?, ?)
                ON CONFLICT (employee_id) DO UPDATE
                SET studio_id = ?;
            """;

    public static final String QUERY_UPDATE_DEVELOPER_CATEGORY =
            """
                UPDATE developer
                SET category_id = ?
                WHERE category_id = ?;
            """;

    public static final String QUERY_UPDATE_DEVELOPER_DEPARTMENT =
            """
                UPDATE developer
                SET department_id = ?
                WHERE department_id = ?;
            """;

    public static final String QUERY_DELETE_EMPLOYEE =
            """
                DELETE FROM employee
                WHERE employee_id = ?;
            """;

    public static final String QUERY_DELETE_DEVELOPER =
            """
                DELETE FROM developer
                WHERE employee_id = ?;
            """;

    public static final String QUERY_DELETE_DIRECTOR =
            """
                DELETE FROM director
                WHERE employee_id = ?;
            """;

    public static final String QUERY_FIND_ALL_BY_STUDIO_ID_WITH_FILTRATION =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                            dev.category_id, dev.department_id, e.active,
                            case when (dir.studio_id is not null) then
                                dir.studio_id
                            else
                                dep.studio_id
                            end as studio_id
                FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                             LEFT JOIN (developer dev NATURAL JOIN department dep)
                             on e.employee_id = dev.employee_id
                WHERE e.employee_id != 0 AND (
                    (dir.studio_id is not null AND %s) OR (dir.studio_id is null AND %s)
                ) AND (%s)
                %s;
            """;

    public static final String QUERY_FIND_ALL_DIRECTORS_BY_STUDIO_ID =
            """
                SELECT employee_id
                FROM director
                WHERE studio_id = ?;
            """;

    public static final String QUERY_FIND_ALL_BY_STUDIO_ID =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                            dev.category_id, dev.department_id, e.active,
                            case when (dir.studio_id is not null) then
                                dir.studio_id
                            else
                                dep.studio_id
                            end as studio_id
                FROM employee e LEFT JOIN director dir on e.employee_id = dir.employee_id
                             LEFT JOIN (developer dev NATURAL JOIN department dep)
                             on e.employee_id = dev.employee_id
                WHERE e.employee_id != 0 AND (
                    (dir.studio_id is not null AND dir.studio_id = ?) OR (dir.studio_id is null AND dep.studio_id = ?)
                );
            """;

    public static final String QUERY_FIND_ALL_BY_GAME_ID =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date,
                            dev.category_id, dev.department_id, e.active, d.studio_id
                FROM (employee e NATURAL JOIN (developer dev NATURAL JOIN department d)) NATURAL JOIN game__employee
                WHERE e.employee_id != 0 AND game_id = ?;
            """;

    public static final String QUERY_FIND_ALL_BY_DEPARTMENT_ID =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date, e.category_id, e.department_id,
                     e.studio_id, e.active
                FROM (employee NATURAL JOIN
                        (
                            SELECT dev.employee_id, dev.category_id, dev.department_id, dep.studio_id
                            FROM (developer dev CROSS JOIN department dep)
                            WHERE dev.department_id = ? AND dev.department_id = dep.department_id
                        ) as dev1
                ) e
                WHERE e.employee_id != 0;
            """;

    public static final String QUERY_FIND_ALL_BY_TEST_APP_ID =
            """
                SELECT e.employee_id, e.first_name, e.last_name, e.birth_date, e.category_id, e.department_id,
                     e.studio_id, e.active
                FROM ((employee NATURAL JOIN (developer NATURAL JOIN department) as dev1
                ) e1 NATURAL JOIN test_app__employee) e
                WHERE e.employee_id != 0 AND e.app_id = ?;
            """;
}
