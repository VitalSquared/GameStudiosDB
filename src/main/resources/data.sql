
INSERT INTO studio (studio_id, name, address) OVERRIDING SYSTEM VALUE VALUES
    (0, 'MAIN STUDIO', 'Main Studio Address')
ON CONFLICT DO NOTHING ^;


INSERT INTO employee (employee_id, first_name, last_name, birth_date, active) OVERRIDING SYSTEM VALUE VALUES
    (0, 'ADMIN', 'ADMIN', '1970-01-01', true)
ON CONFLICT DO NOTHING ^;


INSERT INTO director (employee_id, studio_id) OVERRIDING SYSTEM VALUE VALUES
    (0, 0)
ON CONFLICT DO NOTHING ^;


INSERT INTO account (email, passwd_hash, employee_id) OVERRIDING SYSTEM VALUE VALUES
    ('admin@mail.com', '$2a$12$/OrzpBteN.hwrbZ8enZtSeZpTJTtb03ucwF6N7GfPkb3sez2/HF2S', 0)
ON CONFLICT DO NOTHING ^;


INSERT INTO category (category_id, name) OVERRIDING SYSTEM VALUE VALUES
                                                                          (0, 'Programmer'),
                                                                          (1, 'Animator'),
                                                                          (2, 'Scriptwriter'),
                                                                          (3, 'Software programmer'),
                                                                          (4, 'Game programmer'),
                                                                          (5, 'Audio Designer'),
                                                                          (6, 'Artist'),
                                                                          (7, 'Translator'),
                                                                          (8, 'Quality Assurance'),
                                                                          (9, 'Modeler')
ON CONFLICT DO NOTHING ^;


INSERT INTO genre (genre_id, name) OVERRIDING SYSTEM VALUE VALUES
                                                                    (0, 'Racing'),
                                                                    (1, 'Survival Horror'),
                                                                    (2, 'Stealth'),
                                                                    (3, 'RPG'),
                                                                    (4, 'Fighting'),
                                                                    (5, 'Action Adventure'),
                                                                    (6, 'Hack and Slash'),
                                                                    (7, 'Action Role Playing'),
                                                                    (8, 'Action')
ON CONFLICT DO NOTHING ^;


INSERT INTO platform (platform_id, name, percent) OVERRIDING SYSTEM VALUE VALUES
                                                                                   (0, 'Playstation 4', 5),
                                                                                   (1, 'Playstation 5', 10),
                                                                                   (2, 'Steam', 30),
                                                                                   (3, 'Xbox One', 10),
                                                                                   (4, 'Xbox Series S', 10),
                                                                                   (5, 'Xbox Series X', 10),
                                                                                   (6, 'Origin', 20),
                                                                                   (7, 'Epic Games Store', 5)
ON CONFLICT DO NOTHING ^;


INSERT INTO test_status (status_id, name) OVERRIDING SYSTEM VALUE VALUES
                                                 (0, 'Before Start'),
                                                 (1, 'Started'),
                                                 (2, 'Finished'),
                                                 (3, 'Results'),
                                                 (4, 'Cancelled')
ON CONFLICT DO NOTHING ^;


INSERT INTO test_app_result (result_id, name) OVERRIDING SYSTEM VALUE VALUES
                                                     (0, 'Pending'),
                                                     (1, 'Accepted'),
                                                     (2, 'Failure'),
                                                     (3, 'Success')
ON CONFLICT DO NOTHING ^;