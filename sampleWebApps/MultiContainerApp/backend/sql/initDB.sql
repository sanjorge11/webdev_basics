CREATE SEQUENCE public.item_seq CYCLE INCREMENT 1;

--to insert a next sequence value: nextval('item_seq')

CREATE TABLE public.item
(
    id numeric NOT NULL,
    completed boolean,
    description character varying(255),
    CONSTRAINT item_pkey PRIMARY KEY (id)
);

--CREATE USER postgres_test_user WITH PASSWORD 'password';
