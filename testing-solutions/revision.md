## Bevezetés a JUnit használatába
- Definiáld a unit teszt fogalmát!
- Milyen struktúrát érdemes követni egy teszteset megírásakor?
- Milyen annotációval kell ellátnunk a metódusainkat, illetve milyen beállítást kell elvégezni az alkalmazáson, hogy a teszteseteket működésre bírjuk?
## Az első teszteset létrehozása
- Egy Maven projekt esetén hova kell helyezni a tesztosztályokat?
- Milyen elnevezési konvenciókat érdemes betartani tesztek írásánál?
- Mi a helyzet a láthatósági módosítószavakkal?
## Unit tesztelés ígéretei
- Sorolj fel a unit tesztelés hozadékaiból néhányat!
## Futtatás Mavennel
- Miért tudja a Maven a teszteseteket futtatni?
- Sorold fel egy Maven projekt buildelésének életciklusait!
- Hogyan tudjuk csak a test életciklust futtatni IDEA-ban illetve parancssorból?
## Tesztesetek életciklusa
- Milyen annotációval kell ellátnom egy metódust, hogy minden egyes teszteset előtt lefusson?
- Hogyan lehet meghatározni a tesztesetek lefutási sorrendjét? Melyik ezek közül az alapértelmezett működés?
- Mi történik a tesztosztállyal minden egyes teszt lefutása előtt?
- Mire kell figyelni amikor több tesztesetünk van egy osztályban?
## Assert
- Milyen JUnit assert metódusokat ismersz?
- Hogyan kell/lehet paraméterezni az assertEquals() metódust?
- Hogyan használható az assertAll() metódus?
- Pusztán JUnit használatával hogyan lehet tesztelni objektumokat tartalmazó kollekciókat?
## Kivételkezelés és timeout tesztelése
- Milyen metódust használunk annak eldöntésére, hogy a tesztelendő metódus dobott-e kivételt?
- Hogyan tudom tesztelni, hogy a dobott kivétel a megfelelő hibaüzenetet dobta-e?
- Hogyan tudom tesztelni, hogy egy metódus a megfelelő időn belül futott-e le?
## Egymásba ágyazás
- Hogyan tudjuk csoportosítani a teszteseteinket?
## Tagek és metaannotációk
- A belső osztályokon kívül hogyan tudjuk a teszteseteket csoportosítani?
- Milyen beállítást kell elvégezni a pom.xml-ben hogy csak bizonyos annotációkkal ellátott tesztesetek fussanak le?
- Mi az a metaannotáció?
## Tesztesetek ismétlése
- Milyen paramétereket lehet megadni a @RepeatedTest annotációnak?
- Honnan tudhatjuk, hogy épp hányadik ismétlés zajlik a teszteset futtatásakor?
- Hogyan tudjuk ezt kihasználni, hogy különböző expected - actual párokkal futtassunk egy tesztesetet?
## Paraméterezett tesztek
- Milyen beállítást kell elvégezni a pom.xml-ben hogy paraméterezett teszteket tudjunk futtatni?
- Milyen paramétereket lehet megadni a @ValueSource annotációnak?
- Hogyan lehet megadni enumok értékeit paraméterezett teszteknek?
- A @MethodSource annotációval ellátott teszteknél milyen elvárás van a tesztesetek előállítását végző metódusra?
- Hogyan lehet CSV állományból betölteni teszt eseteket?
## Dinamikus tesztek
- Milyen annotációval kell ellátnom a dinamikus teszteket előállító metódust?
- Milyen példányokat ad vissza ez a metódus?
## Tempdirectory extension
- Hogyan tudok fájlkezeléssel kapcsolatos műveleteket tesztelni?
## JUnit legjobb gyakorlatok (best practice)
- Mik azok a F.I.R.S.T. elvek?
## Hamcrest
- Mi az a Hamcrest? Miért van létjogosultsága?
- Milyen metódus használható egyszerű ellenőrzésekre Hamcrestben? Hogyan kell paraméterezni?
- Mi az a matcher?
- Sorolj fel négy matchert!
- Miért egyszerűbb kollekciókat tesztelni Hamcrest segítségével?
## AssertJ
- Mi az az AssertJ? Miért van létjogosultsága?
- Mit jelent az, hogy az assertek fluentek? Mi az oka, hogy a fejlesztőeszköz tud segíteni egy-egy metódus hívásánál?
- Hogyan tudok kollekciókat tesztelni AssertJ segítségével?
- Mi az a tuple?
- Mire használható a soft assert?
## Mockito
- Mi az a Mockito? Mikor használjuk?
- Mit jelent az, hogy egy implementáció fake?
- Mi a különbség a mock és a stub között?
- Hogyan tudok mock objektumot előállítani?
- Mire való a when() metódus?
- Mire való a verify() metódus?
- Hogyan tudom ellenőrizni, hogy egy metódus megfelelő paraméterekkel lett-e meghívva?