CREATE OR REPLACE FUNCTION add_existed_employee_check()
RETURNS TRIGGER LANGUAGE plpgsql
AS
$$
BEGIN
	IF(EXISTS(SELECT * FROM departments_employees WHERE department_id = NEW.department_id AND employee_id = NEW.employee_id)) THEN
		RETURN NULL;
	END IF;
	RETURN NEW;
END
$$;

CREATE TRIGGER not_add_existed_employee
BEFORE INSERT ON departments_employees
FOR EACH ROW
EXECUTE FUNCTION add_existed_employee_check();

CREATE OR REPLACE FUNCTION update_date() RETURNS trigger LANGUAGE plpgsql
AS
$$
BEGIN
	IF ((date_part('day', NEW.date_end - NEW.date_beg)) < 0) THEN
		RETURN NULL;
	END IF;
	RETURN NEW;
END
$$;

CREATE TRIGGER update_project_end_date
BEFORE UPDATE OF date_end ON projects
FOR EACH ROW
EXECUTE PROCEDURE update_date();