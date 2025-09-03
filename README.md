# metamudel2 prototüüp

Mida prototüüp demonstreerib

1. Toimingute loomine Excelis etteantud tükkidest (millest osa võib olla custom)
2. Excelist JSON Schema (toimingu dokumendi struktuuri) genemine
3. Genetud schema järgi toimingu andmestiku sisestamine
4. Sh objektide registrist info otsimine ja toimingusse sisestamine
5. Back-endis jooksvate funktsioonide rakendamine toimingu andmetele
6. Toimingu salvestamine andmebaasi JSON formaadis + võimalus tekitada nõude objekt
7. Salvestatud toimingu avamine muutmiseks



## Rakenduse jooksutamine

Kui 

### Jooksutamine (lokaalselt arendades)

1. Pane käima andmebaas
docker-compose up postgres

2. Jooksuta rakendust enda IDE-st või

```
./gradlew bootRun
```

#### Jooksutamine Docker-composega
```
docker build -t metamudel2-app -f Dockerfile .
```

#### Running the app and PostgreSQL in Docker (MacOs)
```
docker-compose up -d
```

### Kiikab andmebaasi

URL=jdbc:postgresql://localhost:5432/metamudel2;
USERNAME=admin;
PASSWORD="/run/secrets/db_password"


## Kasutajaliides põhineb sellel raamistikul:

https://github.com/json-editor/json-editor


## Mis osas saaks täiendada

https://github.com/json-editor/json-editor/issues/748


