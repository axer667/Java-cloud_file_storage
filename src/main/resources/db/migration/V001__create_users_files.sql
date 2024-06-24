CREATE SEQUENCE user_sequence START 3 INCREMENT 1;

CREATE TABLE if not exists users(
    id int8 primary key not null,
    login varchar not null,
    password varchar not null,
    first_name varchar,
    last_name varchar,
    role varchar check (role in ('USER', 'ADMIN'))
);

insert into users(id, login, password, first_name, last_name, role)
values (1, 'user1', '$2a$12$wUEkhcvVxfwdjB.23fg8OeODlV2gwBkzazpoUgzalNZpeD6opl4Aq', 'Михаил', 'Стройнов', 'USER');
insert into users(id, login, password, first_name, last_name, role)
values (2, 'user2', '$2a$12$wUEkhcvVxfwdjB.23fg8OeODlV2gwBkzazpoUgzalNZpeD6opl4Aq', 'Василий', 'Пупкин', 'USER');
insert into users(id, login, password, first_name, last_name, role)
values (3, 'admin', '$2a$12$.QVy.KvzfLuIJiKg/jEYseoTmgg.2.x1V.Cq9SRGd/c73f80/o6/K', 'Admin', 'Admin', 'ADMIN');

CREATE SEQUENCE file_sequence START 1 INCREMENT 1;
CREATE TABLE if not exists files(
    id int8 primary key not null,
    user_id int8 not null,
    name varchar,
    size int8,
    type varchar,
    body bytea
);

CREATE SEQUENCE token_sequence START 1 INCREMENT 1;
CREATE TABLE tokens (
    id int8 primary key not null,
    auth_token varchar not null,
    user_id int8 not null
);