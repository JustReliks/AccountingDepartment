CREATE OR REPLACE FUNCTION find_employees_for_department(dep_id bigint)
RETURNS TABLE(id integer, first_name varchar(20), second_name varchar(20),
			  father_name varchar(20), "position" varchar(50),
			  salary numeric(18, 2)) LANGUAGE plpgsql AS
$$
BEGIN
		RETURN QUERY SELECT * FROM employees
		WHERE employees.id IN
		(SELECT employee_id FROM departments_employees d WHERE d.department_id = dep_id);
END
$$
