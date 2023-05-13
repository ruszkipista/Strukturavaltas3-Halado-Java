# Képesítővizsga - Kerti munkák

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

A feladat megtalálható a következő címen: https://github.com/Training360/strukt-val-java-public/tree/master/sv2021-jvjbf-kepesitovizsga-potpot

Új repository-ba dolgozz, melynek neve legyen `sv2021-jvjbf-kepesitovizsga-potpot`!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek).
GroupId: `training360`, artifactId: `sv2021-jvjbf-kepesitovizsga-potpot`. Csomagnév: `training360.gardenservices`.

Először másold át magadhoz a `pom.xml`-t és a teszteseteket, majd commitolj azonnal!
A vizsga végéig bárhányszor commitolhatsz.

Csak a vizsga vége előtt 15 perccel push-olhatsz először, utána push-olhatsz a vizsga végéig bármennyiszer.

Ha letelik az idő mindenképp pusholj, akkor is,
ha nem vagy kész!

## Alkalmazás

Hozz létre egy alkalmazást, amivel egy kertészeti vállalkozástól munkákat lehet megrendelni, és azokra a 
kertész válaszolni tud, illetve a munkát "elvégzett"-re állítani.

## Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal,
Java Spring backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Flyway
* Üzleti logika réteg megvalósítása Service osztályokkal
* Controller réteg megvalósítása, RESTful API implementálására.
* Hibakezelés, validáció
* Hozz létre egy `Dockerfile`-t!

Cheet sheet: https://github.com/Training360/strukt-val-java-public/blob/master/annotations-cheat_sheet.md

## Általános elvárások (9 pont)

Háromrétegű Spring Boot webalkalmazást készíts!

Adatbázist indíthatsz a következő Docker paranccsal:

```shell
docker run -d -e MYSQL_DATABASE=gardeningservices -e MYSQL_USER=gardeningservices -e MYSQL_PASSWORD=gardeningservices -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 --name gardeningservices-mariadb mariadb
```

A feladatleírást olvasd el részletesen, és nézd meg az egyes részfeladatokhoz tartozó teszteseteket is,
hogy milyen inputra mi az elvárt viselkedése az alkalmazásnak!

### Részpontszámok

- Alkalmazás szerkezeti felépítése, következetes mappa és package szerkezet - 1 pont
- Működő és futtatható Spring Boot webalkalmazás - 3 pont
- Clean code - 5 pont

## Kertész létrehozása és lekérdezése (9 pont)

### `POST /api/gardeners`

A kertészről az alábbi adatokat kell elmenteni:

- id (`Long`, adatbázis adja ki)
- név (`String`, nem lehet `null`, üres `String` vagy csak whitespace karakter)

Beküldeni csak a nevét kell!

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és
hiba esetén küldj vissza hibaüzenetet (a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél),
valamint 400-as hibakódot!

Sikeres mentés esetén küldd vissza az elmentett kertész összes adatát (id-val együtt), és 201-es kódot!

Lekérdezés: `GET /api/gardeners`

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Az adatok elmentésre kerülnek - 2 pont
* Validálás és hibakezelés - 2 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

## Kerti munka létrehozása és lekérdezése (12 pont)

### `POST /api/gardeners/{gardenerId}/gardenworks`

A kerti munkáról az alábbi adatokat kell elmenteni:

- id (`Long`, szerver adja ki sorban)
- el van-e végezve (`done`, `boolean` típusú)
- kerti munka szöveges leírása (`description` `String`, nem lehet üres)
- kertész válasza (`answer` `String`, nem lehet üres)
- létrehozás dátuma (`createdAt`, `LocalDateTime`)
- válasz dátuma (`answeredAt`, `LocalDateTime`)

- Beküldeni csak a kerti munka szöveges leírását kell!

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet
(a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Ha a megadott id-nak megfelelő kertész nem található a rendszerben, akkor küldj vissza ugyanúgy 400-as hibakódot és hibaüzenetet!
Ehhez használhatod a validációs hiba formátumát, a pontos hibaüzenetet megtalálhatod a megfelelő tesztesetnél.

Sikeres mentés esetén küldd vissza az elmentett kerti munka adatait (id-val együtt), valamint 201-es státuszkódot!

Lekérdezés: `/api/gardeners/{gardenerId}/gardenworks` címen


### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* Az adatok elmentésre kerülnek - 3 pont
* Egyszerű mezők validálása és hibakezelése - 2 pont
* Nem létező kertész hibájának kezelése - 2 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

## Kerti munka elvégzése (18 pont)

### `PUT /api/gardeners/{gardenerId}/gardenworks/{gardenWorkId}/answer`

A kérés hatására a következő adatok érkeznek:

JSON-ben:

- válasz  (`answer`, `String`, nem lehet üres)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le,
és hiba esetén küldj vissza hibaüzenetet (a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél),
valamint 400-as hibakódot!


Sikeres módosítás esetén az alkalmazás küldjön vissza egy 200-as státuszkódot, valamint a kerti munka adatait.

A megfelelő tesztesetben megtalálod a szükséges elnevezéseket.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- Egyszerű mezők validálása és hibakezelése - 2 pont
- Nem létező kertész hibájának kezelése - 2 pont
- Nem létező kerti munka hibájának kezelése - 2 pont
- Kertész válasza értékének ellenőrzése - 2 pont
- A megfelelő módosítások megtörténnek - 4 pont
- A válasz tartalmazza a megfelelő adatokat - 3 pont

## Kertész neve és kerti munka leírás párok listázása (12 pont)

### `GET /api/pairs`

A kérés hatására az alkalmazás kilistáz bizonyos feltételeknek megfelelő kertész-kerti munka párokat, 200-as státuszkóddal.

Minden logikát egy darab JPQL segítségével kell végrehajtani!

A lekérdezés feltételei:

- A lekérdezésnek rögtön egy dto listát kell visszaadnia, mely tartalmazza a kertész neve és kerti munka leírás párokat.
- Csak a nem elvégzett kerti munkákat adja vissza.
- Név, majd a leírás szerint sorbarendezi.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- A lekérdezés JPQL-t használ - 2 pont
- A lekérdezés dto-val tér vissza - 2 pont
- A lekérdezés tartalmazza a megfelelő szűrést - 1 pont
- A lekérdezés megfelelően sorrendezett - 2 pont
- A válasz tartalmazza a megfelelő adatokat - 2 pont