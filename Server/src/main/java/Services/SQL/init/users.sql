CREATE TABLE IF NOT EXISTS users(
    id BIGINT PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    login CHARACTER VARYING(200) UNIQUE NOT NULL,
    password CHARACTER VARYING(200) NOT NULL
)