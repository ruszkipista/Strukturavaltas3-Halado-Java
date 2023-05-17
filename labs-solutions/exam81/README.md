# Képesítővizsga - Diákok-Dolgozatok api

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

A saját `java-sv3-adv-exams-VezeteknevKeresztnev` repository-dba dolgozz!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek). Projekt, könyvtár
neve legyen: `sv2023-jvjbf-kepesitovizsga`! GroupId: `training360`,
artifactId: `sv2023-jvjbf-kepesitovizsga`.

Ha ezzel kész vagy, azonnal commitolj, a commit szövege legyen: "init". A vizsga végéig bárhányszor commitolhatsz.

Figyelj arra, hogy csomagokban dolgozz!

A feladatra 195 perced van összesen!

Ha letelik az idő, mindenképp commitolj és pusholj, akkor is, ha nem vagy kész! A commit időpontja alapján fogjuk
ellenőrizni a megoldásod. Ha nincs commitod az idő lejárta előtt, akkor sajnos nem tudjuk értékelni a megoldásod! 
(Az idő lejárta után viszont kérünk, hogy ne commitolj, mert ezzel a mi dolgunkat könnyíted meg.)

Csak olyan kódot commitolj, ami le is fordul! Ha nem fordul, arra a részfeladatra nem jár pont.

Ha gondod adódna a GitHubra feltöltéssel, akkor az idő lejárta előtt elküldheted nekünk e-mailben is a
megoldott vizsgafeladatot, .zip formátumban. (De ez csak egy vészhelyzeti lehetőség, alapértelmezett a
GitHubra való feltöltés.)


## Alkalmazás

Hozz létre egy alkalmazást, amivel diákokat és azok dolgozatait tudjuk kezelni!


## Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal, Java Spring Boot backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Liquibase (Ha nem megy a Liquibase, akkor használhatsz Flyway-t a migrációra, de akkor ne felejts el módosítani a `pom.xml`-en!)
* Üzleti logika réteg megvalósítása `@Service` osztályokkal
* Controller réteg megvalósítása, RESTful API implementálására
* Hibakezelés, validáció
* Hozz létre egy `Dockerfile`-t!
* A típusok közötti konverziót `MapStruct`-tal valósítsd meg. (Ha nem megy, használhatsz `ModelMapper`-t, de ekkor módosíts a `pom.xml`-en!)


## Az alkalmazás szerkezeti felépítése (20 pont)

A feladatban két entitást kell elkészítened, egyiket `Student` a másikat `Test` néven. 
Fontos, hogy egy diáknak több dolgozata lehet,
de egy dolgozat csak egy diákhoz tartozhat. A kapcsolat kétirányú legyen!   

`Student` adatai:

* `id (Long)`
* `name (String)`
* `school (School)` lásd később

`Test`adatai:

* `id (Long)`
* `subject (String)`
* `testValue (TestValue)` enum, lásd később; fontos, hogy szövegként legyen lementve

`School` (embedded):

* `schoolName (String)`
* `city (String)`

`TestValue` (enum, minden példányhoz tartozik egy százalékos érték):

*  `PERFECT` - `75`
*  `AVERAGE` - `50`
*  `NOT_PASSED` - `0`;

Háromrétegű Spring Boot webalkalmazást készíts!

Adatbázist indíthatsz a következő Docker paranccsal:

```shell
docker run -d -e MYSQL_DATABASE=schoolrecords -e MYSQL_USER=schoolrecords -e MYSQL_PASSWORD=schoolrecords -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -p 3306:3306 --name schoolrecords-mariadb mariadb
```

A feladatleírást olvasd el részletesen, és nézd meg az egyes részfeladatokhoz tartozó teszteseteket is, hogy milyen
inputra mi az elvárt viselkedése az alkalmazásnak!

### Részpontszámok

* Alkalmazás szerkezeti felépítése, következetes mappa és package szerkezet - 2 pont
* Az entitások létrehozása helyes, `JPA` szabványnak megfelelő - 6 pont
* A három réteg létrehozása megfelelő, indítható, működő Spring Boot alkalmazás - 3 pont
* Dockerfile és migrációs fájlok megléte, helyessége - 4 pont
* Clean code - 5 pont


## Tanuló mentése (12 pont)

### `POST /api/students`

A HTTP kérés törzsében egy tanuló nevét és iskoláját kapjuk. 
Az azonosítót az adatbázis osztja ki, míg a dolgozatok listája kezdetben üres.  

Validálás:

* A név nem lehet üres!

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet (a pontos
hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!
Sikeres mentés esetén küldd vissza az elmentett tanuló összes adatát (id-val és dolgozatokkal együtt), és 201-es kódot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Az adatok elmentésre kerülnek - 3 pont
* Validálás és hibakezelés - 3 pont
* A válasz tartalmazza a megfelelő adatokat - 3 pont


## Dolgozat mentése (20 pont)

### `POST /api/students/{id}/tests`

A tanuló azonosítója az URL-ben érkezik.

A HTTP kérés törzse:
* tantárgy
* dolgozat maxpontszáma (egész)
* az elért pontszám (egész)  

A teszt értékét a maxpontszámból és az aktuális pontszámból számold ki! Jelen esetben ha 75% vagy nagyobb az érték, 
akkor `PERFECT`, ha 50%-nál nagyobb, de nem nagyobb 74%-nál akkor `AVERAGE`, egyéb esetben `NOT_PASSED`.

Ha nem megfelelő a tanuló azonosítója, 404-es státuszkóddal térj vissza és megfelelő hibaüzenettel!

Sikeres mentés esetén küldd vissza a tanuló adatait (id-val és a dolgozattal együtt) és 201-es státuszkódot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* Megfelelően kiszámolja az átlagot - 3 pont
* Az átlag alapján, helyesen, újrafelhasználható módon meghatározza az értéket - 4 pont
* Az adatok elmentésre kerülnek - 3 pont
* Nem létező tanuló hibájának kezelése - 4 pont
* A válasz tartalmazza a megfelelő adatokat - 3 pont


## Dolgozatok lekérése tanulónként (16 pont)

### `GET /api/students/{id}/tests`

A tanuló azonosítója az URL-ben érkezik. Opcionálisan meg lehessen adni query string-ként tantárgyat, 
ekkor csak a tanuló ezen tantárgyának dolgozatait add vissza!   

Ha nem megfelelő a tanuló azonosítója, 404-es státuszkóddal térj vissza és megfelelő hibaüzenettel!

Ha megfelelő, akkor térj vissza a tanuló megfelelő dolgozatainak eredményével! Ha nincs a tantárgyból, 
vagy egyáltalán dolgozata, akkor üres listával térj vissza!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* A query paraméter megfelelően van beállítva és kezelve - 3 pont
* Query paraméter hiánya esetén megfelelő adatokat küld vissza- 6 pont
* Query paraméter megléte esetén megfelelő adatokat küld vissza - 4 pont


## Tanuló iskolát vált (12 pont)

### `PUT /api/students/{id}`

Ha egy tanuló iskolát vált, az addigi eredményeit viszi magával.

A kérés törzsében a következő adatok érkezzenek be:

* `schoolName` (`String`)
* `city` (`String`)

Ha nem megfelelő a tanuló `id`-ja, akkor 404-es státuszkóddal térj vissza és egy megfelelő hibaüzenettel! Egyébként frissítsd
az iskolát a megfelelő értékre és térj vissza a tanuló összes adatával!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Nem létező eredmény kezelése - 3 pont
* Az adatok frissülnek - 4 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont







