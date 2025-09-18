CREATE OR REPLACE FUNCTION asurlus.f_sea_vaikimisi_asur_kood()
    RETURNS TRIGGER AS $$
BEGIN
    -- Kuna rakendus ei toeta multiasurlust ja selleks, et
    -- mitte hard-codeda asurlast, selleks trigger paneb vaikimisi PPA

    IF NEW.asur_kood IS NULL THEN
      NEW.asur_kood = 'PPA';
    END IF;

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;