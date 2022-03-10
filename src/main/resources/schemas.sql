CREATE OR REPLACE FUNCTION create_root_department()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO department (studio_id, head_id, name, is_root) VALUES
        (NEW.studio_id, null, 'Root Department', true);
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_root_department_trigger
    AFTER INSERT ON studio
    FOR EACH ROW
EXECUTE PROCEDURE create_root_department();


CREATE OR REPLACE FUNCTION create_test_applications()
    RETURNS TRIGGER AS $$
BEGIN
    INSERT INTO test_app (studio_id, test_id, result_id)
    SELECT studio_id, NEW.test_id, 0 FROM studio WHERE studio_id != 0;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER create_test_applications_trigger
    AFTER INSERT ON test
    FOR EACH ROW
EXECUTE PROCEDURE create_test_applications();