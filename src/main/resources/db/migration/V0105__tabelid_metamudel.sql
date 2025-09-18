
CREATE TABLE metamudel2.funktsioon (
    funktsioon_id uuid PRIMARY KEY, -- sellele ID-le ei viidata tt tabelitest
    asur_kood varchar(5) NULL, -- kui NULL, siis on globaalne
    koodnimi varchar(240) NOT NULL,
    versioon numeric(5) NOT NULL,
    valjalase varchar NOT NULL,
    kirjeldus text null,
    lisatud timestamp not null,
    lisaja text not null,
    muudetud timestamp null,
    muutja text null,
    CONSTRAINT funktsioon__valjalase__unikaalne UNIQUE NULLS NOT DISTINCT (asur_kood, valjalase)
);
COMMENT ON TABLE metamudel2.funktsioon IS 'Funktsiooni definitsioon. Kui asur_kood on NULL, siis on funktsioon globaalne.';

-- kontrolli, et koodnimi koosneb lubatud märkidest ja ei sisalda punkti
ALTER TABLE metamudel2.funktsioon
    ADD CONSTRAINT funktsioon__koodnimi__lubatud_markidest
        CHECK (koodnimi ~ '^[a-zA-Z0-9_]+$');

-- kontrolli, et valjalase väli = koodnimi + '_v' + versioon
ALTER TABLE metamudel2.funktsioon
    ADD CONSTRAINT funktsioon__valjalase__on_kombinatsioon
        CHECK (valjalase = koodnimi || '_v' || versioon);

-- kontrolli, et globaalse funktsiooni koodnimi algab prefiksiga "glob_"
ALTER TABLE metamudel2.funktsioon
    ADD CONSTRAINT funktsioon__globaalne_koodnimi_algab_glob_prefiksiga
        CHECK (asur_kood IS NOT NULL OR koodnimi LIKE 'glob_%');







CREATE TABLE metamudel2.trykipaigutusfragment (
    trykipaigutusfragment_id uuid PRIMARY KEY, -- sellele ID-le ei viidata tt_* tabelitest
    asur_kood varchar(5) NULL, -- kui NULL, siis on globaalne
    koodnimi varchar(240) NOT NULL,
    versioon numeric(5) NOT NULL,
    valjalase varchar NOT NULL, -- tt_* tabelitest viidatakse sellele
    kirjeldus text null,
    lisatud timestamp not null,
    lisaja text not null,
    muudetud timestamp null,
    muutja text null,
    CONSTRAINT trykipaigutusfragment__valjalase__unikaalne UNIQUE NULLS NOT DISTINCT (asur_kood, valjalase)
);
COMMENT ON TABLE metamudel2.trykipaigutusfragment IS 'Trükimalli taaskasutatav fragment (näiteks päis või jalus)';


-- kontrolli, et koodnimi koosneb lubatud märkidest ja ei sisalda punkti
ALTER TABLE metamudel2.trykipaigutusfragment
    ADD CONSTRAINT trykipaigutusfragment__koodnimi__lubatud_markidest
        CHECK (koodnimi ~ '^[a-zA-Z0-9_]+$');

-- kontrolli, et valjalase väli = koodnimi + '_v' + versioon
ALTER TABLE metamudel2.trykipaigutusfragment
    ADD CONSTRAINT trykipaigutusfragment__valjalase__on_kombinatsioon
        CHECK (valjalase = koodnimi || '_v' || versioon);

-- kontrolli, et globaalse trükipaigutusfragmendi koodnimi algab prefiksiga "glob_"
ALTER TABLE metamudel2.trykipaigutusfragment
    ADD CONSTRAINT trykipaigutusfragment__globaalne_koodnimi_algab_glob_prefiksiga
        CHECK (asur_kood IS NOT NULL OR koodnimi LIKE 'glob_%');



-- TOIMINGU TÜÜP JA SELLEGA SEOTUD TABELID




CREATE TABLE metamudel2.tt (
    tt_id uuid PRIMARY KEY,
    asur_kood varchar(5) NOT NULL,
    koodnimi varchar(240) NOT NULL,
    versioon numeric(5) NOT NULL,
    valjalase varchar NOT NULL,
    nimetus text NOT NULL,
    json_schema text NULL,
    arvutustabelifail text null,
    CONSTRAINT toimingutyyp__valjalase__unikaalne UNIQUE (asur_kood, valjalase)
);
COMMENT ON TABLE metamudel2.tt IS 'Toimingu tüübi (tt) ehk toimingu korrektse struktuuri definitsioon';

-- Column comments

COMMENT ON COLUMN metamudel2.tt.valjalase IS 'Kombinatsioon: <koodnimi>_v<versioon>, nimetuse korrektsuse tabab andmebaasi constraint.';
COMMENT ON COLUMN metamudel2.tt.koodnimi IS 'Toimingu koodnimi ilma versioonita.';
COMMENT ON COLUMN metamudel2.tt.versioon IS 'Toimingu tüübi (major) versioon. Positiivne täisarv.';
COMMENT ON COLUMN metamudel2.tt.nimetus IS 'Toimingu tüübi inimkeelne nimetus';
COMMENT ON COLUMN metamudel2.tt.json_schema IS 'Toimingu tüübi range (kohustuslike väljadega) JSON Schema';
COMMENT ON COLUMN metamudel2.tt.arvutustabelifail IS 'Viide arvutustabeli (Exceli formaadis) failile, millest antud toimingu tüüb on genereeritud.';


-- versioon peab olema positiivne arv
ALTER TABLE metamudel2.tt
    ADD CONSTRAINT tt__versioon__positiivne_arv
        CHECK (versioon > 0);

-- kontrolli, et koodnimi koosneb lubatud märkidest ja ei sisalda punkti
ALTER TABLE metamudel2.tt
    ADD CONSTRAINT tt__koodnimi__lubatud_markidest
        CHECK (koodnimi ~ '^[a-zA-Z0-9_]+$');


-- kontrolli, et valjalase väli = koodnimi + '_v' + versioon
ALTER TABLE metamudel2.tt
    ADD CONSTRAINT tt__valjalase__on_kombinatsioon
        CHECK (valjalase = koodnimi || '_v' || versioon);










CREATE TABLE metamudel2.tt_valideerimisreeglistik(
    tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
    tt_valideerimisreeglistik_id uuid PRIMARY KEY,
    asur_kood varchar(5) NOT NULL,
    sisu text NULL,
    sisu_skriptimiskeel text NULL,
    rasi text not null
);
COMMENT ON TABLE metamudel2.tt_valideerimisreeglistik IS 'Toimingu tüübi sisu valideerimise reeglid, mis ebakorrektselt täidetud sisu korral tagastavd kasutajale veateated.';

COMMENT ON COLUMN metamudel2.tt_valideerimisreeglistik.sisu IS 'Valideerimisreeglid mõnes skriptimiskeeles';
COMMENT ON COLUMN metamudel2.tt_valideerimisreeglistik."sisu_skriptimiskeel" IS 'Millises skriptimiskeeles reeglid on kirjutatud. Näiteks Freemarker.';
COMMENT ON COLUMN metamudel2.tt_valideerimisreeglistik.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';








CREATE TABLE metamudel2.tt_automaatarvutus(
    tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
    tt_automaatarvutus_id uuid PRIMARY KEY,
    asur_kood varchar(5) NOT NULL,
    kaivita_atribuut text NOT NULL,
    vastus_atribuut text NOT NULL,
    sisu text NULL,
    sisu_syntaks text NULL,
    kutsutavad_funktsioonid text null,
    rasi text not null
);
COMMENT ON TABLE metamudel2.tt_automaatarvutus IS 'Milliseid funktsioone milliste sisestusväljade täitmise järel käivitada.';

COMMENT ON COLUMN metamudel2.tt_automaatarvutus.kaivita_atribuut IS 'Millise atribuudi ehk sisestusvälja (mitme puhul komaga eraldatud atribuutide nimekirja) muutmise peale antud automaatarvutus käivitada. Atribuuti tähistab JSONPATH avaldis.';
COMMENT ON COLUMN metamudel2.tt_automaatarvutus.vastus_atribuut IS 'Millise atribuudi väärtuseks panna automaatarvutse vastus.';
COMMENT ON COLUMN metamudel2.tt_automaatarvutus.sisu IS 'Automaatarvutused mõnes skriptimiskeeles. ';
COMMENT ON COLUMN metamudel2.tt_automaatarvutus.sisu_syntaks IS 'Millises skriptimiskeeles automaatarvutused on kirjutatud. Näiteks Freemarker.';
COMMENT ON COLUMN metamudel2.tt_automaatarvutus.kutsutavad_funktsioonid IS 'Komaga eraldatud nimekiri kutsutavatest funktsioonidest ehk nimekiri funktsioon.valjalase väärtustest. Vajalik teada, et toimingu tüübi ühest keskkonnast teise viimisel kõik vajalikud funktsioonid kaasa võtta.';
COMMENT ON COLUMN metamudel2.tt_automaatarvutus.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';








CREATE TABLE metamudel2.tt_trykipaigutus(
                                              tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
                                              tt_trykipaigutus_id uuid PRIMARY KEY,
                                              asur_kood varchar(5) NOT NULL,
                                              sisu text NULL,
                                              sisu_syntaks text NULL,
                                              viidatud_fragmendid text null,
                                              rasi text not null
);
COMMENT ON TABLE metamudel2.tt_trykipaigutus IS 'Trükipaigutuse kirjeldus näit Jasper Reports XML formaadis';

COMMENT ON COLUMN metamudel2.tt_trykipaigutus.sisu IS 'Trükipaigutuse mall.';
COMMENT ON COLUMN metamudel2.tt_trykipaigutus.sisu_syntaks IS 'Millises formateerimise keeles trükipaigutus on kirjutatud. Näiteks JasperReports.';
COMMENT ON COLUMN metamudel2.tt_trykipaigutus.viidatud_fragmendid IS 'Komaga eraldatud nimekiri kasutatud trükipaigutusfragmentidest ehk nimekiri trykipaigutusfragment.valjalase väärtustest. Vajalik teada, et toimingu tüübi ühest keskkonnast teise viimisel kõik vajalikud fragmendid kaasa võtta.';
COMMENT ON COLUMN metamudel2.tt_trykipaigutus.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';






CREATE TABLE metamudel2.tt_ekraanipaigutus(
                                              tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
                                              tt_ekraanipaigutus_id uuid PRIMARY KEY,
                                              asur_kood varchar(5) NOT NULL,
                                              sisu text NULL,
                                              sisu_syntaks text NULL,
                                              viidatud_fragmendid text null,
                                              rasi text not null
);
COMMENT ON TABLE metamudel2.tt_ekraanipaigutus IS 'Muutmisvormi (mis on hard-coded) sisestsuväljade juures kuvatav asuri-spetsiifiline info';

COMMENT ON COLUMN metamudel2.tt_ekraanipaigutus.sisu IS 'Lisainfo.';
COMMENT ON COLUMN metamudel2.tt_ekraanipaigutus.sisu_syntaks IS 'Millises formateerimise keeles lisainfo on kirjeldatud. Näiteks Typescript.';
COMMENT ON COLUMN metamudel2.tt_ekraanipaigutus.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';








CREATE TABLE metamudel2.tt_vastendusetoimikusonumiks(
    tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
    tt_vastendusetoimikusonumiks_id uuid PRIMARY KEY,
    asur_kood varchar(5) NOT NULL,
    sisu text NULL,
    sisu_syntaks text NULL,
    rasi text not null
);
COMMENT ON TABLE metamudel2.tt_vastendusetoimikusonumiks IS 'Reeglid antud tüüpi toimingu põhjal E-toimiku sõnumi kokkupanekuks';

COMMENT ON COLUMN metamudel2.tt_vastendusetoimikusonumiks.sisu IS 'Reeglid, mille alusel toimingust koostada E-toimikusse saadetav sõnum. ';
COMMENT ON COLUMN metamudel2.tt_vastendusetoimikusonumiks.sisu_syntaks IS 'Millises mappimise keeles vastendus on kirjeldatud. Näiteks JSONPATH.';
COMMENT ON COLUMN metamudel2.tt_vastendusetoimikusonumiks.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';







CREATE TABLE metamudel2.tt_etoimikust(
    tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
    tt_etoimikust_id uuid PRIMARY KEY,
    asur_kood varchar(5) NOT NULL,
    sisu text NULL,
    sisu_syntaks text NULL,
    uus_toiming BOOLEAN NOT NULL,
    rasi text not null
);

COMMENT ON TABLE metamudel2.tt_etoimikust IS 'E-toimikust saabuva sõnumi peale antud toiminguks mäppimine.';


COMMENT ON COLUMN metamudel2.tt_etoimikust.tt_id IS 'Viide toimingu tüübile, millist tüüpi toimingu antud vastendamise ehk mäppimise reeglid toodavad.';
COMMENT ON COLUMN metamudel2.tt_etoimikust.sisu IS 'Reeglid, mille alusel toimingust E-toimikust saadud sõnumi alusel toiming või muudatused olemasolevasse toimingusse.';
COMMENT ON COLUMN metamudel2.tt_etoimikust.sisu_syntaks IS 'Millises mappimise keeles vastendus on kirjeldatud. Näiteks JSONPATH.';
COMMENT ON COLUMN metamudel2.tt_etoimikust.uus_toiming IS 'Kas reeglid kehtivad uue toimingu loomise kohta. False puhul tähendab, et reeglid tegelevad juba olemasoleva toimngu täiendamisega E-toimikust saabunud andmetega.';
COMMENT ON COLUMN metamudel2.tt_etoimikust.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';














CREATE TABLE metamudel2.tt_vastendustoimingutyybiks(
    tt_id uuid NOT NULL REFERENCES metamudel2.tt(tt_id),
    tt_vastendustoimingutyybiks_id uuid PRIMARY KEY,
    asur_kood varchar(5) NOT NULL,
    lahtetoimingutyyp_valjalase varchar(255) NOT NULL,
    sisu text NULL,
    sisu_syntaks text NULL,
    rasi text not null
);

COMMENT ON COLUMN metamudel2.tt_vastendustoimingutyybiks.tt_id IS 'Siht toimingu-tüüp, milleks antud vastendusreeglite alusel mingit muud toimingu tüüpi toimingust koostada.';
COMMENT ON COLUMN metamudel2.tt_vastendustoimingutyybiks.lahtetoimingutyyp_valjalase IS 'Lähte toimingutüübi väljalase, millest antud vastendusreeglite alusel toiming koostada';
COMMENT ON COLUMN metamudel2.tt_vastendustoimingutyybiks.sisu IS 'Reeglid, mille alusel uus toiming kokku panna ';
COMMENT ON COLUMN metamudel2.tt_vastendustoimingutyybiks.sisu_syntaks IS 'Millises mappimise keeles vastendus on kirjeldatud. Näiteks JSONPATH.';
COMMENT ON COLUMN metamudel2.tt_vastendustoimingutyybiks.rasi IS 'MD5 räsi, mis on arvutatud kokkukleebitud sisuväljade pealt. Võimaldab tuvastada erinevate asurite samasisulised (ehk ilma muudatusteta) kirjed.';






