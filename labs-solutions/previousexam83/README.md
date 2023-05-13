# Képesítővizsga - Kérdések és válaszok

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

A feladat megtaláltható a következő címen: https://github.com/Training360/strukt-val-java-public/tree/master/sv2021-jvjbf-kepesitovizsga-pot

Új repository-ba dolgozz, melynek neve legyen `sv2021-jvjbf-kepesitovizsga-pot`!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek).
GroupId: `training360`, artifactId: `sv2021-jvjbf-kepesitovizsga-pot`. Csomagnév: `training360.questions`.

Először másold át magadhoz a `pom.xml`-t és a teszteseteket, majd commitolj azonnal!
A vizsga végéig bárhányszor commitolhatsz.

Csak a vizsga vége előtt 15 perccel push-olhatsz először, utána push-olhatsz a vizsga végéig bármennyiszer. 

Ha letelik az idő mindenképp pusholj, akkor is, 
ha nem vagy kész!

## Alkalmazás

Hozz létre egy alkalmazást, amivel egy online oktatáson kérdéseket lehet feltenni, azokra szavazni és 
válaszolni!

## Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal,
Java Spring backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Flyway
* Üzleti logika réteg megvalósítása `@Service` osztályokkal
* Controller réteg megvalósítása, RESTful API implementálására.
* Hibakezelés, validáció
* Hozz létre egy `Dockerfile`-t!

Cheat sheet: https://github.com/Training360/strukt-val-java-public/blob/master/annotations-cheat_sheet.md

## Általános elvárások (9 pont)

Háromrétegű Spring Boot webalkalmazást készíts!

Adatbázist indíthatsz a következő Docker paranccsal:

```shell
docker run -d -e MYSQL_DATABASE=questions -e MYSQL_USER=questions -e MYSQL_PASSWORD=questions -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 --name questions-mariadb mariadb
```

A feladatleírást olvasd el részletesen, és nézd meg az egyes részfeladatokhoz tartozó teszteseteket is, 
hogy milyen inputra mi az elvárt viselkedése az alkalmazásnak! 

### Részpontszámok

- Alkalmazás szerkezeti felépítése, következetes mappa és package szerkezet - 1 pont
- Működő és futtatható Spring Boot webalkalmazás - 3 pont
- Clean code - 5 pont

## Résztvevő létrehozása és lekérdezése (9 pont)

### `POST /api/members`

A résztvevőről az alábbi adatokat kell elmenteni:

- id (`Long`, adatbázis adja ki)
- név (`String`, nem lehet `null`, üres `String` vagy csak whitespace karakter)

Beküldeni csak a nevét kell!

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és 
hiba esetén küldj vissza hibaüzenetet (a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), 
valamint 400-as hibakódot!

Sikeres mentés esetén küldd vissza az elmentett résztvevő összes adatát (id-val együtt), és 201-es kódot!

Lekérdezés: `GET /api/members`

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Az adatok elmentésre kerülnek - 2 pont
* Validálás és hibakezelés - 2 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

## Kérdés létrehozása és lekérdezése (12 pont)

### `POST /api/members/1/questions`

A kérdésről az alábbi adatokat kell elmenteni:

- id (`Long`, szerver adja ki sorban)
- meg van-e válaszolva (`answered`, `boolean` típusú)
- kérdés szövege (`questionText` `String`, nem lehet üres)
- válasz szövege (`answerText` `String`, nem lehet üres)
- létrehozás dátuma (`createdAt`, `LocalDateTime`)
- válasz dátuma (`createdAt`, `LocalDateTime`)

- Beküldeni csak a kérdés szövegét kell!

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet 
(a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Ha a megadott id-nak megfelelő résztvevő nem található a rendszerben, akkor küldj vissza ugyanúgy 400-as hibakódot és hibaüzenetet! 
Ehhez használhatod a validációs hiba formátumát, a pontos hibaüzenetet megtalálhatod a megfelelő tesztesetnél.

Sikeres mentés esetén küldd vissza az elmentett kérdés adatait (id-val együtt), valamint 201-es státuszkódot!

Lekérdezés: `/api/members/1/questions` címen


### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* Az adatok elmentésre kerülnek - 3 pont
* Egyszerű mezők validálása és hibakezelése - 2 pont
* Nem létező résztvevő hibájának kezelése - 2 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont

## Válasz egy kérdésre (18 pont)

### `PUT http://localhost:8080/api/members/1/questions/1/answer`

A kérés hatására a következő adatok érkeznek:

JSON-ben:

- válasz  (`answerText`, `String`, nem lehet üres)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, 
és hiba esetén küldj vissza hibaüzenetet (a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), 
valamint 400-as hibakódot!


Sikeres módosítás esetén az alkalmazás küldjön vissza egy 200-as státuszkódot, valamint a kérdés adatait.

A megfelelő tesztesetben megtalálod a szükséges elnevezéseket.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- Egyszerű mezők validálása és hibakezelése - 2 pont
- Nem létező résztvevő hibájának kezelése - 2 pont
- Nem létező kérdés hibájának kezelése - 2 pont
- Új válasz értékének ellenőrzése - 2 pont
- A megfelelő módosítások megtörténnek - 4 pont
- A válasz tartalmazza a megfelelő adatokat - 3 pont

## Név és kérdés párok listázása (12 pont)

### `GET /api/pairs`

A kérés hatására az alkalmazás kilistáz bizonyos feltételeknek megfelelő kérdéseket, 200-as státuszkóddal.

Minden logikát egy darab JPQL segítségével kell végrehajtani!

A lekérdezés feltételei:

- A lekérdezésnek rögtön egy dto listát kell visszaadnia, mely tartalmazza a név és kérdés szövege párokat.
- Csak a nem megválaszolt kérdéseket adja vissza.
- Név, majd a kérdés szövege szerint sorbarendezi.

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- A lekérdezés JPQL-t használ - 2 pont
- A lekérdezés dto-val tér vissza - 2 pont
- A lekérdezés tartalmazza a megfelelő szűrést - 1 pont
- A lekérdezés megfelelően sorrendezett - 2 pont
- A válasz tartalmazza a megfelelő adatokat - 2 pont
