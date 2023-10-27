CREATE OR REPLACE FUNCTION calculate_profit(project_id bigint) RETURNS NUMERIC(18, 2) LANGUAGE plpgsql AS
$$
DECLARE
	cursor_employees REFCURSOR;
	profit NUMERIC(18,2);
	j NUMERIC(18, 2);
	i RECORD;
	salaries NUMERIC(18,2);

BEGIN
		profit = 0;
		salaries = 0;
		SELECT * INTO i FROM projects WHERE projects.id = project_id;

		OPEN cursor_employees FOR SELECT employees.salary FROM employees
		JOIN departments_employees ON departments_employees.employee_id = employees.id WHERE departments_employees.department_id = i.department_id;
		LOOP
			FETCH cursor_employees INTO j;
			EXIT WHEN NOT FOUND;
			salaries = salaries + j;
		END LOOP;
		CLOSE cursor_employees;
		profit = i.cost - salaries * DATE_PART('month', AGE(i.date_end, i.date_beg));
	RETURN profit;
END
$$