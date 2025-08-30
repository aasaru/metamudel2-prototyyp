CREATE TABLE IF NOT EXISTS product
(
    id                    SERIAL PRIMARY KEY,
    brand                 VARCHAR(255)      NOT NULL,
    category              VARCHAR(255)      NOT NULL,
    name                  VARCHAR(255)      NOT NULL,
    flavour               VARCHAR(255)      NULL,
    calories_per_100_gram NUMERIC(5, 1)     NOT NULL,
    weight_gram           NUMERIC(5, 1)     NOT NULL
);


CREATE TABLE IF NOT EXISTS toimingu_eksemplar
(
    id                    SERIAL PRIMARY KEY,
    toimingu_tyyp         VARCHAR(255)      NOT NULL,
    koostaja              VARCHAR(255)      NOT NULL,
    koostamise_aeg        timestamp         NOT NULL,
    json                  TEXT              NOT NULL
);
-- toimingu tüüp peaks olema koos versiooniga