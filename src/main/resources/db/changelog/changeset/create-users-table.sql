--liquibase formatted sql

--changeset shnirelman:create-users-table

CREATE TABLE IF NOT EXISTS users (
    id bigint NOT NULL,
    name varchar(32),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.users
    OWNER to admin;

CREATE SEQUENCE IF NOT EXISTS users_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.users_seq
    OWNER TO admin;