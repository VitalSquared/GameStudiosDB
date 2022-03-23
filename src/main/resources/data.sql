INSERT INTO category (category_id, name) OVERRIDING SYSTEM VALUE
VALUES
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
ALTER SEQUENCE category_category_id_seq RESTART WITH 10 ^;


INSERT INTO genre (genre_id, name) OVERRIDING SYSTEM VALUE
VALUES
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
ALTER SEQUENCE genre_genre_id_seq RESTART WITH 9 ^;


INSERT INTO platform (platform_id, name, percent) OVERRIDING SYSTEM VALUE
VALUES
        (0, 'Playstation 4', 5),
        (1, 'Playstation 5', 10),
        (2, 'Steam', 30),
        (3, 'Xbox One', 10),
        (4, 'Xbox Series S', 10),
        (5, 'Xbox Series X', 10),
        (6, 'Origin', 20),
        (7, 'Epic Games Store', 5)
ON CONFLICT DO NOTHING ^;
ALTER SEQUENCE platform_platform_id_seq RESTART WITH 8 ^;


INSERT INTO test_status (status_id, name) OVERRIDING SYSTEM VALUE
VALUES
      (0, 'Before Start'),
      (1, 'Started'),
      (2, 'Finished'),
      (3, 'Results'),
      (4, 'Cancelled')
ON CONFLICT DO NOTHING ^;
ALTER SEQUENCE test_status_status_id_seq RESTART WITH 5 ^;


INSERT INTO test_app_result (result_id, name) OVERRIDING SYSTEM VALUE
VALUES
       (0, 'Pending'),
       (1, 'Accepted'),
       (2, 'Failure'),
       (3, 'Success')
ON CONFLICT DO NOTHING ^;
ALTER SEQUENCE test_app_result_result_id_seq RESTART WITH 4 ^;



ALTER SEQUENCE department_department_id_seq RESTART WITH 1 ^;


INSERT INTO studio (studio_id, name, address) OVERRIDING SYSTEM VALUE
VALUES
    (0, 'MAIN STUDIO', '2207 Bridgepointe Pkwy Foster City, CA'),
    (1, 'Naughty Dog', '2425 Olympic Blvd Santa Monica CA 90404'),
    (2, 'Insomniac Games', '2255 N Ontario St. Suite 550 Burbank, CA 91504'),
    (3, 'Santa Monica Studio', '13031 W Jefferson Blvd Los Angeles CA 90094'),
    (4, 'Sucker Punch Productions', '929 108th Ave NE #300, Bellevue, WA 98004'),
    (5, 'Guerrilla Games', 'Nieuwezijds Voorburgwal 225 1012 RL Amsterdam The Netherlands')
ON CONFLICT DO NOTHING ^;
ALTER SEQUENCE studio_studio_id_seq RESTART WITH 6 ^;


INSERT INTO employee (employee_id, first_name, last_name, birth_date, active) OVERRIDING SYSTEM VALUE
VALUES
    (0, 'ADMIN', 'ADMIN', '1970-01-01', true),
    (26, 'Kimora', 'Knott', '1983-11-01', true),
    (27, 'Shereen', 'Dorsey', '1979-12-29', true),

    (1, 'Yisroel', 'Mackay', '1987-04-27', true),

    (2, 'Mekhi', 'Ratliff', '1983-12-11', true),
    (3, 'Jean-Luc', 'David', '1999-06-29', true),

    (4, 'Anwen', 'Connolly', '1999-06-29', true),
    (5, 'Ela', 'Mata', '1993-02-03', true),


    (6, 'Kathy', 'Pace', '1994-02-10', true),

    (7, 'Khaleesi', 'Taylor', '1970-09-09', true),
    (8, 'Maciej', 'Beech', '1977-10-09', true),

    (9, 'Nikodem', 'Bird', '1966-12-26', true),
    (10, 'Clay', 'Pham', '1954-01-22', true),


    (11, 'Sabah', 'Donaldson', '1987-03-09', true),

    (12, 'Ellesha', 'Romero', '1955-10-18', true),
    (13, 'Sean', 'Foley', '1968-05-17', true),

    (14, 'Annika', 'Workman', '1955-10-10', true),
    (15, 'Adem', 'Pitts', '1959-06-08', true),


    (16, 'Sofie', 'Wilde', '1967-12-11', true),

    (17, 'Emmett', 'Wolfe', '2000-07-03', true),
    (18, 'Cherry', 'Cresswell', '1961-04-11', true),

    (19, 'Enzo', 'Felix', '1966-01-13', true),
    (20, 'Alysha', 'Novak', '1962-11-25', true),


    (21, 'Jeffery', 'Grainger', '1959-07-16', true),

    (22, 'Nikola', 'Cobb', '1968-06-07', true),
    (23, 'Libbi', 'Charles', '1990-01-31', true),

    (24, 'Harper', 'Woodward', '1958-11-18', true),
    (25, 'Tiago', 'Crowther', '1984-07-21', true)
ON CONFLICT DO NOTHING ^;
ALTER SEQUENCE employee_employee_id_seq RESTART WITH 28 ^;


UPDATE department SET head_id = 2 WHERE studio_id = 1;
UPDATE department SET head_id = 7 WHERE studio_id = 2;
UPDATE department SET head_id = 12 WHERE studio_id = 3;
UPDATE department SET head_id = 17 WHERE studio_id = 4;
UPDATE department SET head_id = 22 WHERE studio_id = 5;
INSERT INTO department (department_id, studio_id, head_id, name, is_root) OVERRIDING SYSTEM VALUE
VALUES
    (7, 1, 4, 'Naughty Dog Department 2122', false),
    (8, 2, 9, 'Insomniac Department 3144', false),
    (9, 3, 14, 'Santa Monica Department 1211', false),
    (10, 4, 19, 'Sucker Punch Department 8788', false),
    (11, 5, 24, 'Guerrilla Department 6655', false);
ALTER SEQUENCE department_department_id_seq RESTART WITH 12 ^;


INSERT INTO director (employee_id, studio_id) OVERRIDING SYSTEM VALUE
VALUES
    (0, 0),
    (26, 0),
    (27, 0),
    (1, 1),
    (6, 2),
    (11, 3),
    (16, 4),
    (21, 5)
ON CONFLICT DO NOTHING ^;


INSERT INTO developer (employee_id, category_id, department_id) OVERRIDING SYSTEM VALUE
VALUES
    (2, 0, 2),
    (3, 1, 2),

    (4, 5, 7),
    (5, 9, 7),


    (7, 0, 3),
    (8, 1, 3),

    (9, 5, 8),
    (10, 9, 8),


    (12, 0, 4),
    (13, 1, 4),

    (14, 5, 9),
    (15, 9, 9),


    (17, 0, 5),
    (18, 1, 5),

    (19, 5, 10),
    (20, 9, 10),


    (22, 0, 6),
    (23, 1, 6),

    (24, 5, 11),
    (25, 9, 11)
ON CONFLICT DO NOTHING ^;


INSERT INTO account (email, passwd_hash, employee_id) OVERRIDING SYSTEM VALUE
VALUES
    ('admin@mail.com', '$2a$12$/OrzpBteN.hwrbZ8enZtSeZpTJTtb03ucwF6N7GfPkb3sez2/HF2S', 0),
    ('kimora.knott@mail.com', '$2a$12$.u4gm19ToXuyObmi4PE4Y.UWwIA0Cc9D1HJ/ijN3h7sTBdGaAWMEW', 26),
    ('shereen.dorsey@mail.com', '$2a$12$6l4Czwpr6g8pkUXEFJJeQ.Hw9aC6/JypVVy889/FKxSKeK0dYYAt6', 27),


    ('yisroel.mackay@mail.com', '$2a$12$lq2UUURrvDez2mgJnpSEA.LjkwkgRV7qjPoSVjFtLQaTRna/Vyjd6', 1),

    ('mekhi.ratliff@mail.com', '$2a$12$CsJF7RCIUuUzYShH2FmCb.tKjNofGmmaqZdY//A1gkTWtLkzXP1s2', 2),
    ('jean-luc.david@mail.com', '$2a$12$ScpafjxglMWeCJPV.g/az.jq2xbfDxozmAQZQUQKYbXJbvYWvJHvK', 3),

    ('anwen.connolly@mail.com', '$2a$12$6ejD4oZW3yumigEDlw8j4u8eSfFgqRzTOvHmTXhfk95RjJx2KIZCK', 4),
    ('ela.mata@mail.com', '$2a$12$87wtw5OjE.keS9/KgjyVVu5.F./RyHQuXbKuZgmuP5BXbjRK.Uqeu', 5),


    ('kathy.pace@mail.com', '$2a$12$ZHhq4SzLRqlLPgws5CoQyehqR9YEZfPdfzWQh/nC31Ec4MjSrhoMe', 6),

    ('khaleesi.taylor@mail.com', '$2a$12$Rkj0H3lQ5md5jS8l4cSLHOp.sTU.ZBq17/ZAEvHetnprbtQ3mCv7C', 7),
    ('maciej.beech@mail.com', '$2a$12$IsANuQBpszxmlKXBBuJkqeK3UBh5yk.LitPOvlsNTGel/J7sIYe0q', 8),

    ('nikodem.bird@mail.com', '$2a$12$SbhVCfds3NNv6QsO3qBmxOv4OIDUQHhPciK8j0bWBGbmVcA6A2XWO', 9),
    ('clay.pham@mail.com', '$2a$12$cdl6zMhKuqfUZb72Qde7UeAsAYT92gHnxD1YhefBBUsTL63RjyJgK', 10),


    ('sabah.donaldson@mail.com', '$2a$12$neQfzsPeJ/l9QQe2j4HQ.eJ49PjB9MGLZTxZDvZeJdTYnVbJgIIqq', 11),

    ('ellesha.romero@mail.com', '$2a$12$cJpq5bIz/QR/0zICDDp2AuRN76x2dZQHjlJLIb/lkLXBOc3/Fy0K2', 12),
    ('sean.foley@mail.com', '$2a$12$30G39xIRM1KJRxcruG/ZCuXucKSdFZiLOW8QOXVJO4SaXjR2QdGFS', 13),

    ('annika.workman@mail.com', '$2a$12$.oxkt27QICaKjXCvuoaQLup9B.TKq7miOOR.gWA79UDBTT7Z03/tS', 14),
    ('adem.pitts@mail.com', '$2a$12$tBxfrLIgy/fHm9LgxD8DTOF4L/KFlWoFREu9JBI8YGO2/E0GqmEQ.', 15),


    ('sofie.wilde@mail.com', '$2a$12$asfUmVdZ/EDgRqHmmi6EeOaCklVO/ZXYTxpYCoI7ePh3gVBMtQ2vC', 16),

    ('emmett.wolfe@mail.com', '$2a$12$27fUTTltP8JSMIofIhQFQuxgSG9mkTZnaiyYWScIEM8tCWSHf7Hc6', 17),
    ('cherry.cresswell@mail.com', '$2a$12$weaIZ.jKxXkJVh1qT.k1xebGE6dWlprEnhhFvUaML4LDhSJlEozPi', 18),

    ('enzo.felix@mail.com', '$2a$12$Ziqxowy/54wFTBppepz4z.N1zdfP60AQeUehNaThrNcR2Q55v2/zG', 19),
    ('alysha.novak@mail.com', '$2a$12$Qows6NW08Ogz2esf5SYROuZP7ImEac7aSqxIyyh1A5dmJ9r/peHgW', 20),


    ('jeffery.grainger@mail.com', '$2a$12$6fZZ.PLFRlfDZRZZozOpGu4uJuT0YOTEbe0l12zw2i1ZRGn4jZU.a', 21),

    ('nikola.cobb@mail.com', '$2a$12$yupusO2jPEhUuCe5p52/EONm2pw3HPqR5dtF/dPIbUEGJHfwg/SaO', 22),
    ('libbi.charles@mail.com', '$2a$12$t43S6Wxxn432Jy7veFtBRu7EPMg7PTEuiS0cG5EDJSKRTiklLFG66', 23),

    ('harper.woodward@mail.com', '$2a$12$Lyqp5dr.3XEs/oz/ttQTouUWK3x2TZHw5I9H8dZLfUbd3yVsXG17u', 24),
    ('tiago.crowther@mail.com', '$2a$12$FeY.BoNZGagZqkH8oYYds.LLgoDEXAlfL.hfw1tHwZMuwHhwR0BhS', 25)
ON CONFLICT DO NOTHING ^;


INSERT INTO game (game_id, start_date, dev_name, release_name, expenses, studio_id) OVERRIDING SYSTEM VALUE
VALUES
       (0, '2011-01-01', 'TLOU', 'The Last of Us', 1000000, 1),
       (1, '2014-01-01', 'TLOU2', 'The Last of Us Part II', 2000000, 1),
       (2, '2014-01-01', 'Spider-Man', 'Marvels Spider-Man', 1000000, 2),
       (3, '2014-01-01', 'GOW4', 'God of War', 3000000, 3),
       (4, '2015-01-01', 'GOT', 'Ghost of Tsushima', 1000000, 4),
       (5, '2011-01-01', 'Horizon', 'Horizon Zero Dawn', 1500000, 5) ^;
ALTER SEQUENCE game_game_id_seq RESTART WITH 6 ^;


INSERT INTO game__genre (game_id, genre_id)
VALUES
       (0, 5),
       (1, 5),
       (2, 5),
       (3, 5),
       (3, 6),
       (4, 5),
       (4, 2),
       (5, 7) ^;


INSERT INTO game__employee (game_id, employee_id)
VALUES
       (0, 2),
       (0, 3),
       (1, 4),
       (1, 5),
       (2, 7),
       (2, 8),
       (2, 9),
       (2, 10),
       (3, 14),
       (3, 15),
       (4, 17),
       (4, 18),
       (4, 20),
       (5, 22),
       (5, 23),
       (5, 24),
       (5, 25) ^;


INSERT INTO message (message_id, date, topic, content, sender, attachments) OVERRIDING SYSTEM VALUE
VALUES
       (0, '2021-12-12 09:00:05', 'Hello Studio!', 'Welcome to Naughty Dog Studio', 'yisroel.mackay@mail.com', null),
       (1, '2021-12-13 10:00:05', 'Hello Marvelous Studio!', 'Welcome to Insomniac Games Studio', 'kathy.pace@mail.com', null),
       (2, '2021-12-14 11:05:05', 'Hello Godly Studio', 'Welcome to Santa Monica Studio', 'sabah.donaldson@mail.com', null),
       (3, '2021-12-15 11:10:15', 'Hello Dear Studio', 'Welcome to Sucker Punch Studio', 'sofie.wilde@mail.com', null),
       (4, '2021-12-16 09:10:25', 'Hello Studio', 'Welcome to Guerrilla Games Studio', 'jeffery.grainger@mail.com', null),
       (
        5,
        '2021-12-17 12:25:00',
        'Dear Directors!',
        'The system will be offline between 2021-12-17, 15:00:00 and 18:00:00. Attachments contains useful information!',
        'admin@mail.com',
        '[{"name":"file1.txt","id":0,"content":"Q09OVEVOVCBPRiBGSUxFIDE="}]'::JSON
        ) ^;
ALTER SEQUENCE message_message_id_seq RESTART WITH 6 ^;


INSERT INTO received_message (message_id, receiver, read)
VALUES
       (0, 'mekhi.ratliff@mail.com', false),
       (0, 'jean-luc.david@mail.com', false),
       (0, 'anwen.connolly@mail.com', false),
       (0, 'ela.mata@mail.com', true),

       (1, 'khaleesi.taylor@mail.com', false),
       (1, 'maciej.beech@mail.com', true),
       (1, 'nikodem.bird@mail.com', true),
       (1, 'clay.pham@mail.com', false),

       (2, 'ellesha.romero@mail.com', false),
       (2, 'sean.foley@mail.com', true),
       (2, 'annika.workman@mail.com', false),
       (2, 'adem.pitts@mail.com', true),

       (3, 'emmett.wolfe@mail.com', false),
       (3, 'cherry.cresswell@mail.com', true),
       (3, 'enzo.felix@mail.com', false),
       (3, 'alysha.novak@mail.com', false),

       (4, 'nikola.cobb@mail.com', false),
       (4, 'libbi.charles@mail.com', false),
       (4, 'harper.woodward@mail.com', true),
       (4, 'tiago.crowther@mail.com', true),

       (5, 'yisroel.mackay@mail.com', true),
       (5, 'kathy.pace@mail.com', false),
       (5, 'sabah.donaldson@mail.com', true),
       (5, 'sofie.wilde@mail.com', false),
       (5, 'jeffery.grainger@mail.com', false),
       (5, 'kimora.knott@mail.com', true),
       (5, 'shereen.dorsey@mail.com', true) ^;


INSERT INTO test (test_id, start_date, end_date, grand, min_studios_num, apps_deadline, status_id) OVERRIDING SYSTEM VALUE
VALUES
       (0, '2011-01-01', '2011-03-01', 500000, 1, '2010-12-24', 3),
       (1, '2013-03-01', '2013-05-01', 500000, 2, '2013-02-25', 4),
       (2, '2015-03-15', '2015-05-15', 1000000, 2, '2015-03-01', 3),
       (3, '2018-05-21', '2018-08-21', 1000000, 2, '2018-05-01', 3) ^;
ALTER SEQUENCE test_test_id_seq RESTART WITH 4 ^;

DELETE FROM test_app;

INSERT INTO test_app (app_id, studio_id, test_id, result_id) OVERRIDING SYSTEM VALUE
VALUES
    (0, 1, 0, 3),
    (1, 2, 0, 2),
    (2, 2, 2, 3),
    (3, 3, 2, 3),
    (4, 5, 2, 3),
    (5, 1, 3, 3),
    (6, 4, 3, 3),
    (7, 2, 3, 2) ^;
ALTER SEQUENCE test_app_app_id_seq RESTART WITH 8 ^;

INSERT INTO test (test_id, start_date, end_date, grand, min_studios_num, apps_deadline, status_id) OVERRIDING SYSTEM VALUE
VALUES
    (4, '2022-04-18', '2022-06-18', 1500000, 1, '2022-04-10', 1) ^;
ALTER SEQUENCE test_test_id_seq RESTART WITH 5 ^;


INSERT INTO test__genre (test_id, genre_id)
VALUES
       (0, 5),
       (2, 5),
       (2, 7),
       (3, 2),
       (3, 5),
       (4, 5) ^;


INSERT INTO test_app__employee (app_id, employee_id)
VALUES
       (0, 2),
       (0, 3),
       (1, 7),
       (1, 8),
       (2, 7),
       (2, 8),
       (3, 12),
       (3, 14),
       (4, 24),
       (4, 25),
       (5, 2),
       (5, 3),
       (6, 18),
       (6, 20),
       (7, 9),
       (7, 10) ^;


INSERT INTO contract (contract_id, date, percent, test_id) OVERRIDING SYSTEM VALUE
VALUES
       (0, '2011-03-05', 5, 0),
       (1, '2015-05-20', 10, 2),
       (2, '2018-05-05', 7, 3) ^;
ALTER SEQUENCE contract_contract_id_seq RESTART WITH 3 ^;


INSERT INTO contract__game (game_id, contract_id)
VALUES
       (0, 0),
       (2, 1),
       (3, 1),
       (5, 1),
       (1, 2),
       (4, 2) ^;


INSERT INTO game__contract__platform (game_id, contract_id, platform_id, release_date, cost, sold_count)
VALUES
       (0, 0, 0, '2013-06-14', 30, 1000000),
       (1, 2, 0, '2020-06-19', 60, 2000000),
       (1, 2, 1, '2021-05-21', 60, 500000),
       (2, 1, 0, '2018-09-07', 40, 2000000),
       (2, 1, 1, '2020-11-12', 60, 400000),
       (3, 1, 0, '2018-04-20', 60, 2000000),
       (3, 1, 1, '2022-01-14', 60, 500000),
       (4, 2, 0, '2020-07-17', 60, 2500000),
       (5, 1, 0, '2017-03-01', 40, 2500000),
       (5, 1, 2, '2020-08-07', 60, 3000000) ^;

