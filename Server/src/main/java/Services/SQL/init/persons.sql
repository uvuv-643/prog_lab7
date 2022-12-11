CREATE TABLE IF NOT EXISTS coordinates (
    id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    x FLOAT NOT NULL,
    y FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS locations (
    id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    x DOUBLE PRECISION NOT NULL,
    y INTEGER NOT NULL,
    title CHARACTER VARYING(200) NOT NULL
);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'country') THEN
        CREATE TYPE country AS ENUM (
            'VATICAN',
            'THAILAND',
            'RUSSIA',
            'CHINA'
        );
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'color') THEN
        CREATE TYPE color AS ENUM (
            'BLACK',
            'BLUE',
            'YELLOW'
        );
    END IF;
END$$;

CREATE TABLE IF NOT EXISTS persons (
    id INTEGER PRIMARY KEY NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 ),
    person_order SERIAL NOT NULL,
    coordinates_id INTEGER NOT NULL,
    height INTEGER NOT NULL,
    weight FLOAT NOT NULL,
    color color,
    country country,
    location_id INTEGER,
    user_id INTEGER NOT NULL,
    created_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMPTZ NOT NULL DEFAULT NOW(),
    CONSTRAINT coordinates_foreign_key FOREIGN KEY (coordinates_id) REFERENCES coordinates(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT location_foreign_key FOREIGN KEY (location_id) REFERENCES locations(id) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT user_id_foreign_key FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE OR REPLACE FUNCTION trigger_set_timestamp()
    RETURNS TRIGGER AS $$
BEGIN
    NEW.updated_at = NOW();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER set_timestamp
BEFORE UPDATE ON persons
FOR EACH ROW
EXECUTE PROCEDURE trigger_set_timestamp();