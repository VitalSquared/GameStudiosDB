
CREATE TABLE IF NOT EXISTS category (
    category_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(64) NOT NULL
) ^;

CREATE TABLE IF NOT EXISTS employee (
    employee_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    birth_date DATE NOT NULL,
    active BOOL DEFAULT true
) ^;

CREATE TABLE IF NOT EXISTS studio (
    studio_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(128) NOT NULL,
    address VARCHAR(128) NOT NULL
) ^;

CREATE TABLE IF NOT EXISTS department (
    department_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    studio_id BIGINT NOT NULL references studio,
    head_id BIGINT references employee,
    name VARCHAR(64) NOT NULL DEFAULT '',
    is_root BOOLEAN DEFAULT FALSE
) ^;

CREATE TABLE IF NOT EXISTS director (
    employee_id BIGINT PRIMARY KEY references employee,
    studio_id BIGINT references studio
) ^;

CREATE TABLE IF NOT EXISTS developer (
    employee_id BIGINT PRIMARY KEY references employee,
    category_id BIGINT references category,
    department_id BIGINT references department
) ^;

CREATE TABLE IF NOT EXISTS test_status (
    status_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(64) NOT NULL
) ^;

CREATE TABLE IF NOT EXISTS test_app_result (
    result_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(64) NOT NULL
) ^;

CREATE TABLE IF NOT EXISTS test (
    test_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL CHECK (end_date > start_date),
    grand NUMERIC(11, 2),
    min_studios_num BIGINT CHECK (min_studios_num > 0),
    apps_deadline DATE NOT NULL CHECK (apps_deadline < start_date),
    status_id BIGINT references test_status
) ^;

CREATE TABLE IF NOT EXISTS test_app (
    app_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    studio_id BIGINT references studio,
    test_id BIGINT references test,
    result_id BIGINT references test_app_result
) ^;

CREATE TABLE IF NOT EXISTS test_app__employee (
    app_id BIGINT references test_app,
    employee_id BIGINT references employee,
    PRIMARY KEY (app_id, employee_id)
) ^;

CREATE TABLE IF NOT EXISTS game (
    game_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    start_date DATE NOT NULL,
    dev_name VARCHAR(128) NOT NULL,
    release_name VARCHAR(128),
    expenses NUMERIC(11, 2),
    studio_id BIGINT references studio
) ^;

CREATE TABLE IF NOT EXISTS game__employee (
    game_id BIGINT references game,
    employee_id BIGINT references employee,
    PRIMARY KEY (game_id, employee_id)
) ^;

CREATE TABLE IF NOT EXISTS genre (
    genre_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(128) NOT NULL
) ^;

CREATE TABLE IF NOT EXISTS test__genre (
    test_id BIGINT references test,
    genre_id BIGINT references genre,
    PRIMARY KEY (test_id, genre_id)
) ^;

CREATE TABLE IF NOT EXISTS game__genre (
    game_id BIGINT references game,
    genre_id BIGINT references genre,
    PRIMARY KEY (game_id, genre_id)
) ^;

CREATE TABLE IF NOT EXISTS contract (
    contract_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date DATE NOT NULL,
    percent BIGINT CHECK (percent between 0 and 100),
    test_id BIGINT references test
) ^;

CREATE TABLE IF NOT EXISTS contract__game (
    game_id BIGINT references game,
    contract_id BIGINT references contract,
    PRIMARY KEY (game_id, contract_id)
) ^;

CREATE TABLE IF NOT EXISTS platform (
    platform_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    name VARCHAR(64) NOT NULL,
    percent BIGINT CHECK (percent between 0 and 100)
) ^;

CREATE TABLE IF NOT EXISTS game__contract__platform (
    game_id BIGINT references game,
    contract_id BIGINT references contract,
    platform_id BIGINT references platform,
    release_date DATE,
    cost NUMERIC(5, 2) CHECK (cost > 0),
    sold_count BIGINT,
    PRIMARY KEY (game_id, platform_id)
) ^;

CREATE TABLE IF NOT EXISTS account (
    email VARCHAR(64) PRIMARY KEY NOT NULL CHECK (length(email) > 0),
    passwd_hash VARCHAR(64) NOT NULL CHECK (length(passwd_hash) > 0),
    employee_id BIGINT references employee
) ^;

CREATE TABLE IF NOT EXISTS message (
    message_id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY,
    date TIMESTAMP NOT NULL,
    topic VARCHAR(256) NOT NULL,
    content VARCHAR(4096) NOT NULL,
    sender VARCHAR(64) NOT NULL CHECK (length(sender) > 0) references account,
    attachments json
) ^;

CREATE TABLE IF NOT EXISTS received_message (
    message_id BIGINT references message,
    receiver VARCHAR(64) NOT NULL CHECK (length(receiver) > 0) references account,
    read boolean default false,
    PRIMARY KEY (message_id, receiver)
) ^;



CREATE OR REPLACE FUNCTION create_root_department()
    RETURNS TRIGGER AS
    $$
        BEGIN
            INSERT INTO department (studio_id, head_id, name, is_root) VALUES
                (NEW.studio_id, null, NEW.name || ' Root Department', true) ;
            RETURN NEW ;
        END ;
    $$
    LANGUAGE plpgsql ^;

DROP TRIGGER IF EXISTS create_root_department_trigger
    ON studio ^;
CREATE TRIGGER create_root_department_trigger
    AFTER INSERT ON studio
    FOR EACH ROW
EXECUTE PROCEDURE create_root_department() ^;



CREATE OR REPLACE FUNCTION create_test_applications()
    RETURNS TRIGGER AS
    $$
        BEGIN
            INSERT INTO test_app (studio_id, test_id, result_id)
            SELECT studio_id, NEW.test_id, 0 FROM studio WHERE studio_id != 0 ;
            RETURN NEW ;
        END ;
    $$
    LANGUAGE plpgsql ^;

DROP TRIGGER IF EXISTS create_test_applications_trigger
    ON test ^;
CREATE TRIGGER create_test_applications_trigger
    AFTER INSERT ON test
    FOR EACH ROW
EXECUTE PROCEDURE create_test_applications() ^;
