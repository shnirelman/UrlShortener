--liquibase formatted sql

--changeset shnirelman:create-urls-table

CREATE TABLE IF NOT EXISTS urls (
    id bigint NOT NULL,
    user_id bigint NOT NULL,
    short_form varchar(32),
    long_form varchar(256),
    used_at timestamp,
    CONSTRAINT urls_pkey PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS public.urls
    OWNER to admin;

CREATE SEQUENCE IF NOT EXISTS urls_seq
    INCREMENT 50
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.urls_seq
    OWNER TO admin;