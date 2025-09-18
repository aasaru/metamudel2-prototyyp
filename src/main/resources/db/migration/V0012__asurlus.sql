
CREATE TABLE IF NOT EXISTS asurlus.asur
(
    asur_kood             VARCHAR(5) PRIMARY KEY,
    nimi                  VARCHAR(255)  NOT NULL,
    registrikood          VARCHAR(20) NOT NULL
);

COMMENT ON TABLE asurlus.asur IS 'Multiasurluse (multi-tenancy) üürnike nimekiri. Enamik ülejäänud tabeleid sisaldavad asur_id välja, mis viitab üürnikule.';

COMMENT ON COLUMN asurlus.asur.asur_kood IS 'Asuri (tenant) identifikaator. Valitud on sõne (string), et tagada parem loetavus ja asuri sama identifikaator erinevates keskkondades (arendus, test, toodang)';

-- peab koosnema suurtähtedest, alakriipsust ja numbritest
ALTER TABLE asurlus.asur
    ADD CONSTRAINT asur__kood__lubatud_markidest
        CHECK (asur_kood ~ '^[A-Z0-9_]+$');