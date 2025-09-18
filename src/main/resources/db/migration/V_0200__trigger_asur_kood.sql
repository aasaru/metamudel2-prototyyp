-- triggerid, mis täidavad asur_kood väärtuse
CREATE TRIGGER menetluse_baaskomponent.trg_vaikimisi__asur_kood__toimingueksemplar
    BEFORE INSERT OR UPDATE ON menetluse_baaskomponent.toimingueksemplar
    FOR EACH ROW
EXECUTE FUNCTION asurlus.f_sea_vaikimisi_asur_kood();



CREATE TRIGGER trg__asur_kood__metamudel__toimingutyyp
    BEFORE INSERT OR UPDATE ON metamudel2.toimingutyyp
    FOR EACH ROW
EXECUTE FUNCTION asurlus.f_sea_vaikimisi_asur_kood();