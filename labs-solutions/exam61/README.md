# Vizsgafeladat

A feladatok megoldásához az IDEA fejlesztőeszközt használd! Bármely régebbi osztályt megnyithatsz.

A saját `java-sv3-adv-exams-VezeteknevKeresztnev` repository-dba dolgozz! 
Ezen könyvtár tartalmát nyugodtan át lehet másolni (a `pom.xml`-t, a JPA feladatokhoz a teszteket, 
illetve a teszteléses feladatokhoz a tesztelendő osztályokat). Projekt, könyvtár 
neve legyen: `sv3-halado-2023-jvjbf-felezovizsga`. GroupId: `training360`, artifactId: `sv3-halado-2023-jvjbf-felezovizsga`.

Hozz letre egy uj adatbazis semat:
```SQL
USE mysql;
CREATE SCHEMA IF NOT EXISTS exam61 DEFAULT CHARACTER SET utf8;
CREATE USER 'training'@'localhost' IDENTIFIED BY 'training';
GRANT ALL ON *.* TO 'training'@'localhost';
```

Ha ezzel kész vagy, azonnal commitolj, a commit szövege legyen: "init".

Az egyes feladatokat külön csomagba szervezd! A csomagneveket a feladat leírásában találod. A JPA feladatokhoz 
tartoznak előre megírt tesztosztályok, illetve a teszteléses feladatokhoz megkapod a tesztelendő osztályokat. 
Ezekkel a megoldásod helyességét ellenőrizheted.

A feladatra 2,5 órád van összesen (150 perc)! Az utolsó commit időpontja legkésőbb 12:15-kor legyen!

Ha letelik az idő, mindenképp commitolj, akkor is, ha nem vagy kész! A commit időpontja alapján fogjuk
ellenőrizni a megoldásod. Ha nincs commitod az idő lejárta előtt, akkor sajnos nem tudjuk értékelni a megoldásod!

Csak olyan kódot commitolj, ami le is fordul! Ha nem fordul, arra a részfeladatra nem jár pont.


## JPA

### Entitások konfigurálása (16 pont)

Ebben az alkalmazásban zenekarokat és zenészeket fogunk tárolni adatbázisban.  

Készítsd el az `Album` osztályt (nem entitás), aminek van egy címe és egy megjelenés dátuma!   

Készítsd el `Band` nevű entitást. Minden zenekarnak legyen egy neve, egy műfaja
ami enum típusú (`POP, ROCK, HIP_HOP`), ezen kívül legyen neki egy albumok listája és egy 
metódusa, amivel az albumot lehet hozzáadni a listához!  

Készítsd el a `Musician` nevű entitást, aminek legyen egy neve, egy születési dátuma és
egy hangszere, amin játszik (`String`)!  

Alakíts ki kapcsolatot a két entitás között úgy, hogy egy zenész egy zenekarban játszhat,
de egy zenekarban lehet több zenész! A kapcsolat kétirányú legyen! 


### Repository osztály, alapműveletek (16 pont)

Készítsd el a `BandMusicianRepository` osztályt, a tanult architektúrának megfelelő attribútummal!  

Legyen benne egy `Band saveBand(Band band)` nevű metódus, ami egy zenekart ment le
az adatbázisba! Figyelj arra, hogy ha a zenekar tartalmaz nem mentett zenészt, akkor az is mentésre kerüljön!
Ehhez módosíthatod az entitásokat. Azt is valósítsd meg a `Band` osztályban, hogy a zenekarhoz lehessen hozzáadni zenészt!

Legyen benne egy `Band findBandById(long bandId)` metódus, ami egyszerűen visszaad
egy zenekart az azonosítója alapján! (Kapcsolódó osztályok nem kellenek.)

Legyen benne egy `Musician updateBandWithMusician(long bandId, Musician musician)` metódus, ami már egy mentett zenekarhoz 
képes zenészt hozzárendelni! 

Legyen benne egy `Band findBandWithAllMusicians(long bandId)`, ami id alapján lekérdezi a zenekart az összes zenésszel együtt! 


### További műveletek (16 pont)

Továbbra is a repository osztályban dolgozz!   

Lehessen egy zenészt törölni az adatbázisból a `void deleteMusicianById(long musicianId)` metódus segítségével! 

Legyen egy metódus, ami betölt egy zenekart azokkal az albumokkal, amik egy paraméterül átadott dátum után jelentek meg! 

Legyen egy `List<Band> findBandsWithMusicianName(String name)` metódus, ami visszaadja
azokat a zenekarokat egy listában, akiknek a zenészeinek nevében szerepel a paraméterül átadott név részlet! 


## Tesztelés (32 pont)

Adott egy alkalmazás, ami tanulókat tart nyilván. Az alkalmazás három rétegű lesz, ezek közül jelenleg
a `StudentService` osztály van kész. A `StudentRepository` osztályban csak metódusok vázai vannak meg. 
A `StudentService` osztály függ a `StudentRepository` osztálytól, ezért kell, hogy a `StudentRepository` 
osztály létezzen.

Írj unit teszteket a `Student` és `StudentService` osztályhoz! 

A következő metódusokat kell tesztelned:

- Teszteld a `Student` osztályban található `addGradeWithSubject()` metódust!
- Teszteld a `Student` osztályban található `countNumberOfGrades()` metódust, ami a tanuló összes jegyét adja vissza! 
- Teszteld a `StudentService` osztályban található `saveNewStudent()` metódust, hogy megfelelő paraméterrel történt-e hívás!
- Teszteld a `StudentService` osztályban található `calculateStudentAverageBySubject()` metódust! 
  Figyelj, hogy a metódus áthív a repository-ba! Írj tesztesetet minden lehetséges kimenetre!
- Teszteld a `StudentService` osztályban található `findStudentsWithMoreGradesThan()` metódust! 
  A metódus áthív a repository-ba, ahonnan az összes tanulót kapja vissza. 

A tesztekhez bármilyen eszközt használhatsz. A tesztelendő osztályokban alkalmazott megoldások nem biztos, 
hogy a leghatékonyabbak, de ezzel nincs teendőd, ebben a feladatban a hangsúly a tesztelésen van. 
