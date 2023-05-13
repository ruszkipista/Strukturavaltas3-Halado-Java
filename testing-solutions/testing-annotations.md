# Annotációk – Cheat sheet
## JUnit
* `@Test` - Tesztmetódusra, ezáltal látja a JUnit, hogy ez egy tesztmetódus.  
* `@BeforeEach` - Az ilyen annotációval ellátott metódust a JUnit minden egyes  tesztmetódus előtt lefuttatja.  
* `@AfterEach` - Az ilyen annotációval ellátott metódust a JUnit minden egyes  tesztmetódus után lefuttatja.  
* `@BeforeAll` - Az ilyen annotációval ellátott metódus csak egyszer fut le, az adott  osztályban szereplő összes teszteset lefutása előtt.  
* `@AfterAll` - Az ilyen annotációval ellátott metódus csak egyszer fut le, az adott  osztályban szereplő összes teszteset lefutása után.  
* `@DisplayName()` - Tesztesetre tehetjük rá, paraméterként pedig megadhatjuk neki azt a  nevet, amelyet szeretnénk, hogy a lefuttatáskor megjelenjen a fejlesztőeszközben vagy  a reportban.  
* `@DisplayNameGeneration()` - Tesztesetre tehetjük rá, paraméterként pedig  megadhatjuk neki, hogy az adott metódus- vagy osztálynévből milyen módon generálja  a lefuttatáskor a fejlesztőeszközben vagy a reportban megjelenő nevet. Vannak rá  beépített implementációk, de magunk is írhatunk egyet.  
* `@Disabled` - Ha nem szeretnénk, hogy egy tesztosztály vagy egy konkrét teszteset  lefusson.  
* `@DisabledXXX` - A teszteset kikapcsolása valamilyen feltétel alapján (Pl.  `@DisabledOnOs(OS.WINDOWS)`)  
* `@TestMethodOrder()` - Akkor használjuk, ha valamiért fontos megadnunk a tesztesetek  lefutási sorrendjét. `MethodOrderer` implementációval lehet paraméterezni.  
  - `MethodOrderer.Alphanumeric`: ábécé-sorrendben futtatja le a teszteseteket;  
  - `MethodOrderer.OrderAnnotation`: egy egész számokkal paraméterezhető  `@Order` annotációt kell a tesztesetekre rakni, a lefuttatás a számok sorrendjében  történik;  
   - `MethodOrderer.Random`: véletlenszerű sorrend, ez az alapértelmezett).  
* `@TestFactory` - Ha `DynamicTest` típusú, dinamikus teszteset példányokat szeretnék  kapni.  
* `@RepeatedTest()` - Ha azt szeretnénk, hogy egy teszteset többször fusson le  (különböző bemeneti paraméterekkel). Első paraméterül egy egész számmal kell  megadni, hogy hányszor szeretnénk a lefutást.  
* `@ParameterizedTest` - Paraméterezett tesztek írására. Egy name paraméterként  megadhatjuk neki a teszteset nevét is, hogy hogyan kerüljön megjelenítésre a 
felhasználói felületen vagy a reportban. Ezenkívül még egy további annotációkat kell  egy ilyen tesztesetre rátenni, amelyben megadjuk a paramétereket, hogy milyen  értékekkel futtassa le a JUnit ezt a tesztesetet:  
  - `@ValueSource()`: Ennek az annotációnak egyszerű értékeket lehet paraméterül  megadni (strings, ints, doubles, stb.) Nem csak a beviteli értékeket lehet  megadni, hanem az elvárt értékeket is, második paraméterként.  
  - `@NullSource`: Ha szeretnénk, hogy a JUnit meghívja az adott tesztmetódust null értékkel is  
  - `@EmptySource`: Ha String vagy kollekció a paraméter típusa, ekkor üres stringgel  vagy kollekcióval fogja meghívni a metódust.  
  - `@NullAndEmptySource`: Mindkét módon, tehát null értékű és üres stringgel vagy  kollekcióval is meghívja az adott metódust.  
  - `@EnumSource()`: Ezen annotáció használatával megadhatjuk egy enum  különböző értékeit is paraméterként.  
  - `@MethodSource()`: Paraméterül egy statikus metódus nevét kell neki megadni. A  paraméterezett teszt meghívásához a paramétereket ez a metódus fogja  előállítani. Ez a metódus egy Stream, egy Collection, egy Iterator vagy egy  Iterable példányt adhat vissza.  
  - `@ArgumentsSource`: Ezzel az annotációval egy ArgumentsProvider implementációt adhatunk át paraméterül. Ennek az interfésznek egyetlen  metódusa van, amellyel egy Stream formájában adhatjuk meg a paramétereket.  
  - `@CsvSource`: Ezzel az annotációval van lehetőség az állomány tartalmát  String[] formájában közvetlenül a forráskódban megadni.  
  - `@CsvFileSource`: Ennek az annotációnak pedig egy, a classpath-on lévő CSV-fájlt  adhatunk meg. Ezenkívül több más paraméter is megadható, például a  karakterkódolás, vagy hogy milyen típusú sortörés karakterrel szeretnénk  dolgozni.  
* `@Nested` - Hierarchiát építhetünk föl a tesztesetek között belső osztályok használatával.  Ez akkor hasznos, ha valamiféleképpen csoportosítani szeretnénk a teszteseteinket, és  a különböző csoportok inicializációjakor van egy közös rész is, illetve csoportonként  pedig egy különböző rész is. A belső osztályokat kell ezzel az annotációval ellátni.  
* `@Tag` - A JUnit unit tesztjeit el lehet látni tag-ekkel is, ezzel csoportosítva a  teszteseteket. Ezt az annotációt rátehetjük az egész tesztosztályra vagy egyes  tesztesetekre is, egy helyen egyszerre akár többet is.  
* Metaannotációk létrehozásakor meg kell adni:  
  - az új annotáció nevét (public `@interface ServiceTest`, ezzel a `@ServiceTest` annotáció jön létre.)  
  - mire tehető rá a létrehozott új annotáció (`@Target(ElementType.TYPE,  ElementType.METHOD)`, vagyis osztályra és metódusra),  
  - mikor kerüljön feldolgozásra ez az új annotáció (`@Retention(RetentionPolicy.RUNTIME)`, vagyis futásidőben), 
  - azt, hogy tesztesetnél akarjuk használni ezt az annotációt (`@Test`)  
  - kerülhetnek rá további más annotációk is, amelyeket az általunk létrehozott  metaannotáció ezáltal „hordozni” fog,  
  - és kerülhet rá tag is (pl. `@Tag("service")`).  
* `@TempDir` - Fájlműveletek teszteléséhez kapunk ezáltal egy ideiglenes könyvtárat. Ez  úgy működik, hogy a JUnit a teszteset lefutása előtt létrehoz egy könyvtárat az adott  operációs rendszer temp könytárán belül, elvégzi benne a fájlműveletek tesztelését, a  teszteset végén pedig letörli ezt a könyvtárat. 

## AssertJ  
* `@ExtendWith(SoftAssertionsExtension.class)`: A tesztosztályra lehet rátenni ezt az  annotációt. Soft assertek írásához kap az osztály parameter injectionnel egy  SoftAssertions példányt.

## Mockito  
* `@ExtendWith(MockitoExtension.class)` - A tesztosztályra kell rátenni, és ekkor nem  mi hozzuk létre a mock objektumot, hanem a Mockito.  
* `@Mock` - A mockolni kívánt osztályt felvesszük attribútumként, és ezzel az annotációval  látjuk el. Ebből tudja a Mockito, hogy ezt az osztályt kell majd mockolni.  
* `@InjectMocks` - Ezzel az annotációval pedig azt az osztályt vesszük fel attribútumként,  amelynek a mockolt osztályt dependency injectionnel át akarjuk adni. 
