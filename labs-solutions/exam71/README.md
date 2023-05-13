# Záróvizsga - Repülőjáratok api

A feladatok megoldásához az IDEA fejlesztőeszközt használd!
Bármely régebbi osztályt megnyithatsz.

A saját `java-sv3-adv-exams-VezeteknevKeresztnev` repository-dba dolgozz!
Ezen könyvtár tartalmát nyugodtan át lehet másolni (`pom.xml`, tesztek). Projekt, könyvtár
neve legyen: `sv2023-jvjbf-zarovizsga`! GroupId: `training360`,
artifactId: `sv2023-jvjbf-zarovizsga`.

Ha ezzel kész vagy, azonnal commitolj, a commit szövege legyen: "init". A vizsga végéig bárhányszor commitolhatsz.

Figyelj arra, hogy csomagokban dolgozz.
A feladatra 195 perced van összesen! Az utolsó commit időpontja legkésőbb 13:00-kor legyen! (Utána kérünk, hogy 
ne commitolj, mert ezzel a mi dolgunkat könnyíted meg.)

Ha letelik az idő, mindenképp commitolj, akkor is, ha nem vagy kész! A commit időpontja alapján fogjuk
ellenőrizni a megoldásod. Ha nincs commitod az idő lejárta előtt, akkor sajnos nem tudjuk értékelni a megoldásod!

Csak olyan kódot commitolj, ami le is fordul! Ha nem fordul, arra a részfeladatra nem jár pont.

Ha gondod adódna a GitHubra feltöltéssel, akkor 13:00 óra előtt elküldheted nekünk e-mailben is a 
megoldott vizsgafeladatot, .zip formátumban. (De ez csak egy vészhelyzeti lehetőség, alapértelmezett a 
GitHubra való feltöltés.)


## Alkalmazás

Hozz létre egy alkalmazást,amivel repülőgépeket és ezekhez rendelt utakat lehet adminisztrálni!

## Nem-funkcionális követelmények

Klasszikus háromrétegű alkalmazás, MariaDB adatbázissal, Java Spring backenddel, REST webszolgáltatásokkal.

Követelmények tételesen:

* SQL adatbázis kezelő réteg megvalósítása Spring Data JPA-val (`Repository`)
* Liquibase (Ha nem megy a Liquibase, akkor használhatsz Flyway-t a migrációra, de akkor ne felejtsd el módosítani a pom.xml-en!)
* Üzleti logika réteg megvalósítása `@Service` osztályokkal
* Controller réteg megvalósítása, RESTful API implementálására
* Hibakezelés, validáció
* Hozz létre egy `Dockerfile`-t!
* A típusok közötti konverziót `MapStruct`-al valósítsd meg. (Ha nem megy, használhatsz `ModelMapper`-t, de ekkor módosíts a pom.xml-en!) 


## Általános követelmények (12 pont)

- Alkalmazás szerkezeti felépítése, következetes mappa és package szerkezet - 2 pont
- A három réteg létrehozása megfelelő, indítható, működő Spring Boot alkalmazás - 3 pont
- Dockerfile és migrációs fájlok megléte, helyessége - 3 pont
- Clean code - 4 pont


## Az adatbázisréteg elkészítése  (12 pont)

A feladatban két entitást kell elkészítened egyiket `Airplane` a másikat `Route` néven. Fontos, hogy egy repülőgéphez
több út is tartozhat, de egy utat csak egy repülőgép tesz meg. A kapcsolat kétirányú legyen!  

`AirplaneType` (enum)

* A következő értékei lehetnek `BOEING_747, BOEING_787, AIRBUS_A380, AIRBUS_A340`
* Az enumban legyen egy `numberOfPassengers` attribútum is melynek értéke rendre: 600, 200, 800, 300

Repülő adatai

* `id` (`Long`)
* `airplaneType` (`AirplaneType`)
* `ownerAirline` (`String`)

Út adatai:

* `id` (`Long`)
* `departureCity` (`String`)
* `arrivalCity` (`String`)
* `dateOfFlight` (`LocalDate`)


Adatbázist indíthatsz a következő Docker paranccsal:

```shell
docker run -d -p 3306:3306 -e MYSQL_DATABASE=airplanes -e MYSQL_USER=airplanes -e MYSQL_PASSWORD=airplanes -e MYSQL_ALLOW_EMPTY_PASSWORD=yes --name airplanes-mariadb mariadb
```

### Részpontszámok

- Az entitások létrehozása helyes, JPA szabványnak megfelelő - 8 pont
- Tábla és oszlopnevek konvencióknak megfelelők - 4 pont


## Repülő és Út mentése (30 pont)

### `POST /api/airplanes`

A HTTP kérés törzsében egy repülőgép adatait várjuk. Az azonosítót az adatbázis osztja ki, az utak listája kezdetben üres.   
Validálás:

- A légitársaság neve nem lehet üres

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet (a pontos
hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Sikeres mentés esetén küldd vissza a lementett repülőt az összes adatával (id-val és utakkal) együtt, és 201-es
státuszkódot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Az adatok elmentésre kerülnek - 3 pont
* Validálás és hibakezelés - 3 pont
* A válasz tartalmazza a megfelelő adatokat - 3 pont

### `POST /api/airplanes/{id}/routes`

A repülő azonosítója az URL-ben érkezik.

A HTTP kérés törzse:

- induló város (nem lehet üres)
- érkezési város (nem lehet üres)
- a repülés időpontja (csak jövőbeli lehet)

A kérésben beérkező adatokat a fenti feltételek alapján validáld le, és hiba esetén küldj vissza hibaüzenetet
(a pontos hibaüzeneteket megtalálhatod a vonatkozó teszteseteknél), valamint 400-as hibakódot!

Ha nem található a repülő, 404-es státuszkóddal térj vissza és megfelelő hibaüzenettel!

Vizsgáld meg, hogy ennek a repülőnek a menteni kívánt napra van-e már útja! Ha nincs, akkor mentsd el az utat, ha van, akkor térj vissza
400-as státuszkóddal és a megfelelő üzenettel!

Sikeres mentés esetén küldd vissza a mentett út adatait (repülő nélkül, de id-val) és 201-es státuszkódot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
* Az adatok elmentésre kerülnek, ha megfelelők - 3 pont
* Egyszerű mezők validálása és hibakezelése - 3 pont
* Nem létező repülő hibájának kezelése - 4 pont
* Az út meglétének ellenőrzése - 3 pont
* A válasz tartalmazza a megfelelő adatokat - 2 pont


## Repülők lekérdezése (12 pont)

### `GET /api/airplanes`

Lehessen az összes repülőt lekérdezni a végponton az utakkal együtt!
Opcionálisan, query stringként lehessen megadni egy légitársaságot!

### Részpontok

* A beérkező HTTP kérést az alkalmazás kezeli (controller) - 3 pont
* Query paraméter nélkül az összes repülő lekérdezésre kerül - 3 pont
* A query paraméter esetén megfelelő szűrés történik - 3 pont
* A válasz tartalmazza a megfelelő adatokat - 3 pont


## Járat törlése (14 pont)

### `PUT /api/airplanes/{id}/routes/{routeId}`

Elképzelhető, hogy egy út mégsem valósul meg valamilyen okból. Ebben az esetben az út bent marad az adatbázisban,
de nem lesz hozzá repülőgép rendelve. 

Ha a repülő vagy az út a kérés alapján nem található, küldj vissza 404-es hibakódot! Figyelj arra,
hogy a lekért útnak a repülőhöz kell tartoznia! Ha nem így lenne, akkor is 404-es hibakód menjen vissza!

Sikeres frissítés után küldd vissza a repülő összes adatát, természetesen a törölt út már ne szerepeljen!

### Részpontok

- A beérkező HTTP kérést az alkalmazás kezeli - 3 pont
- Megfelelő hibakezelés - 4 pont
- Megfelelő frissítés  - 4 pont
- A válasz tartalmazza a megfelelő adatokat - 3 pont




