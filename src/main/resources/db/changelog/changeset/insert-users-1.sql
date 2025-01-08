--liquibase formatted sql

--changeset shnirelman:insert-users-1

TRUNCATE users;

INSERT INTO users(id, name) VALUES
(1, 'User_1'),
(2, 'User_2'),
(3, 'User_3');