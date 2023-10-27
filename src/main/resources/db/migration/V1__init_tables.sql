CREATE TABLE users(
    id serial NOT NULL CONSTRAINT users_pk PRIMARY KEY,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE departments(
	id serial NOT NULL CONSTRAINT departments_pk PRIMARY KEY,
	name VARCHAR(20) NOT NULL
);

CREATE TABLE employees(
	id serial NOT NULL CONSTRAINT employees_pk PRIMARY KEY,
	first_name VARCHAR(20) NOT NULL,
	last_name VARCHAR(20) NOT NULL,
	father_name VARCHAR(20) NOT NULL,
	position VARCHAR(50) NOT NULL,
	salary NUMERIC(18, 2) NOT NULL
);

CREATE TABLE departments_employees(
	id serial NOT NULL CONSTRAINT departments_employees_pk PRIMARY KEY,
	department_id integer NOT NULL,
	employee_id	integer NOT NULL,
	FOREIGN KEY (department_id) REFERENCES departments (id) ON DELETE CASCADE,
	FOREIGN KEY (employee_id) REFERENCES employees (id) ON DELETE CASCADE
);

CREATE TABLE projects(
	id serial NOT NULL CONSTRAINT projects_pk PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	cost NUMERIC(18, 2) NOT NULL,
	department_id integer,
	date_beg timestamp NOT NULL,
	date_end timestamp,
	date_end_real timestamp,
	FOREIGN KEY (department_id) REFERENCES departments (id),
	CHECK (date_end > date_beg AND date_end_real > date_beg)
);