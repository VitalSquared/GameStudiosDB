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