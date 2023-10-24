CREATE TABLE roles(
    id serial not null constraint role_pk primary key,
    name varchar not null
);

INSERT INTO roles values (1, 'VIEWER'), (2, 'EDITOR');

create table users_roles(
    id serial not null constraint users_roles_pk primary key,
    user_id integer not null,
    role_id integer not null,
    foreign key (user_id) references users (id),
    foreign key (role_id) references roles (id)
);