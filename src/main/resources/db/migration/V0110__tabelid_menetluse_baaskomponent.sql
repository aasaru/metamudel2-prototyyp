
CREATE TABLE IF NOT EXISTS menetluse_baaskomponent.toimingueksemplar
(
    toimingueksemplar_id   UUID PRIMARY KEY,
    asur_kood              VARCHAR(5)        NOT NULL,
    toimingutyyp_valjalase VARCHAR(255)      NOT NULL,
    koostaja               VARCHAR(255)      NOT NULL,
    koostamise_aeg         timestamp         NOT NULL,
    json                   TEXT              NOT NULL
);


COMMENT ON TABLE menetluse_baaskomponent.toimingueksemplar IS 'Toiming';

-- Column comments

COMMENT ON COLUMN menetluse_baaskomponent.toimingueksemplar.toimingutyyp_valjalase IS 'Viide toimingu tüübi väljalaskele';

