# Annotációk – Cheat sheet  
## Alap Spring Framework és Spring Boot annotációk  
* @ComponentScan - Osztályra tehető rá. A Spring az adott csomagban és az alatta lévő  csomagokban található összes Spring Beant megtalálja és felolvassa, vagyis azokat az  osztályokat, amelyeken rajta van a következő annotációk valamelyike: @Component,  @Repository, @Service, @Controller vagy @RestController.  
* @Component - Ez jelzi az általános beaneket.  
* @Repository - Ez jelzi a perzisztens rétegbe tartozó beaneket.  
* @Service - Ez jelzi az üzleti logika rétegbe tartozó beaneket.  
* @Controller - Ez pedig a prezentációs rétegbe tartozó beaneket.  
* @RestController - A @Controller annotációt egészíti ki további funkcionalitással  (REST webszolgáltatások használata esetén, ld. később).  
* @Configuration - Osztályra tehető rá, ebben az osztályban tudunk egyedi  konfigurációt Java kóddal megadni. Egy ilyen osztályban lehet megadni Spring  Beaneket is a @Bean annotációval.  
* @Bean - Ha rátesszük egy metódusra, akkor a metódus által visszaadott objektumot a  Spring Spring Beanként fogja kezelni, azaz elhelyezi az Application Contextben.  
* @Autowired - Attribútumra, konstruktorra vagy setter metódusra tehető rá egy  komponensben. A Spring ennek az attribútumnak fog értéket adni, vagy ezt a  konstruktort vagy metódust fogja meghívni, azaz elvégezi az injectiont.  
* @SpringBootApplication - Azt az osztályt jelöljük vele, amelyben az alkalmazás  belépési pontja, a main() metódus található. Metaannotáció, vagyis több más annotáció  is található rajta:  
  – @EnableAutoConfiguration: Ha a classpath-on talál valamilyen library-t,  amihez van konfigurációja, akkor azt automatikusan konfigurálja föl, automatikusan tegye elérhetővé a fejlesztő számára.  
  – @SpringBootConfiguration: Ezen az annotáción rajta van a @Configuration annotáció, ami azt jelenti, hogy ebben az osztályban is tudunk konfigurációt Java  kóddal megadni.  
  – @ComponentScan: Ld. korábban.  
## Lombok  
* @Data - Osztályra tehető. Kódot generál, egyszerre helyettesíti a következő  annotációkat:  
– @ToString
– @EqualsAndHashCode 
– @Getter (minden attribútumon)  
– @Setter (minden nem final attribútumon)  
– @RequiredArgsConstructor 
* @NoArgsConstructor - Osztályra tehető. Legenerálásra kerül egy paraméter nélküli  konstruktor.  
* @AllArgsConstructor - Osztályra tehető. Legenerálásra kerül egy olyan konstruktor,  amely minden attribútumnak paraméter által kezdőértéket ad.  
* @Slf4j - Osztályra tehető. Legenerálásra és példányosításra kerül egy `Logger` típusú  attribútum.  
## Spring MVC  
* @RequestMapping - Ezzel tudjuk megmondani, hogy milyen URL-en figyeljen az adott  metódus. Ezt meg lehet adni osztályszinten is, ekkor az összes metódusra vonatkozik.  Ha mindkét helyen szerepel (osztály- és metódusszinten), akkor konkatenálódik, tehát  összeadódik.  
* @GetMapping - GET HTTP kérésre válaszoló metódusra teendő rá, a @RequestMapping annotáció továbbfejlesztése.  
* @PostMapping - POST HTTP kérésre válaszoló metódusra teendő rá, a @RequestMapping annotáció továbbfejlesztése.  
* @PutMapping - PUT HTTP kérésre válaszoló metódusra teendő rá, a @RequestMapping annotáció továbbfejlesztése.  
* @DeleteMapping - DELETE HTTP kérésre válaszoló metódusra teendő rá, a  @RequestMapping annotáció továbbfejlesztése.  
* @ResponseBody - Ha egy metódusból visszatérünk, azt egy template-névként próbálja a  Spring értelmezni. Akkor kell a metódusra rátenni ezt az annotációt, ha meg akarjuk  mondani a Springnek, hogy a visszatérési értéket alakítsa át JSON formátumúvá. Azért,  hogy ezt ne kelljen ténylegesen minden metóduson megtenni, létrehozták a  @RestController annotációt, amely a @Controller annotációt annyival egészíti ki,  hogy az osztályban lévő összes metódust implicit ellátja ezzel az annotációval.  
* @RequestParam - Ahhoz, hogy az URL-nek paramétereket adhassunk át, a controller  osztály metódusában fel kell vennünk egy paramétert és ellátni ezzel az annotációval.  Arról is dönthetünk, hogy ez egy kötelező paraméter legyen vagy csak opcionális.  
* @PathVariable - Ha URL részleteket akarunk megadni a @GetMapping annotáció  paramétereként. Itt, amikor definiáljuk, hogy milyen URL-en legyen elérhető az adott  metódus, akkor egy kapcsos zárójelekkel megadott placeholdert kell használnunk.  Természetesen itt is működik a típuskonverzió.  
* @RequestBody - Ahhoz, hogy a HTTP kérés törzsében adatot lehessen beküldeni. Az  adatot lehet pl. JSON formátumban beküldeni, és a HTTP válaszban visszakapott adat is  ilyen módon érkezik meg.  
* @ResponseStatus(HttpStatus.XXX) - Megadhatjuk, milyen HTTP státuszkóddal térjen  vissza a metódus.
* @ExceptionHandler - Controller osztályon belüli metódusra tehető rá, lokális  kivételkezelést jelölünk vele.  
* @ControllerAdvice - Globális hibakezelő osztályra tehető rá, az ilyen osztályban  @ExceptionHandler annotációval ellátott hibakezelő metódusok találhatóak.  
## Integrációs tesztelés  
* `@SpringBootTest` - Spring Boot integrációs tesztosztályokat kell ezzel jelölni. Ezáltal a  teljes alkalmazás el fog indulni. Ez egy metaannotáció, szerepel rajta egy  @ExtendWith(SpringExtension.class) annotáció. Ez azt mondja meg a JUnit 5-nek,  hogy ezt a tesztosztályt az SpringExtension-nel kell lefuttatni. Paraméterül meg lehet  adni azt is, hogy a Tomcat konténer egy random porton kerüljön elindításra (amelyik  éppen szabad). (`@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)`)  
* @LocalServerPort - Amennyiben tudni szeretnénk, hányas porton fut a konténer,  akkor a port számát ezzel az annotációval injektálhatjuk a tesztesetbe.  
* @AutoConfigureMockMvc - Abban az esetben, ha a teljes alkalmazást szeretnénk  tesztelni, de konténer nélkül, a @SpringBootTest annotációt használjuk, az  @AutoConfigureMockMvc annotációt pedig azért, hogy a MockMvc keretrendszer  automatikusan fel legyen konfigurálva. Ebben az esetben nem kell mockolni a service  osztályunkat, mert a valódi service-t használjuk, az kerül lepéldányosításra.  
* @WebMvcTest - Spring MVC használatakor a controller réteg teszteléséhez kell a  tesztosztályra rátenni.  
* @MockBean - A mock objektumokat kell ezzel az annotációval ellátni. A Mockito  keretrendszerrel lehet létrehozni és paraméterezni őket.  
## Swagger UI  
* @Tag - Ezzel az annotációval lehet megadni egyedi elnevezést a controller osztálynak,  hogy a dokumentációban mi jelenjen meg.  
* @Schema() - Személyre szabható, hogy az egyes attribútumok mellett mi jelenjen meg  névként a felhasználói felületen, sőt, egy értéket is meg lehet adni példaként.  
* @Operation - Magáról az adott metódusról ezzel az annotációval tudunk megadni  rövidebb és hosszabb leírást, amely bele fog kerülni a dokumentációba.  
* @ApiResponse - Ezzel az annotációval felvehetjük, hogy a metódus milyen további  státuszkódokkal képes még visszatérni, ezek mit jelentenek, és a dokumentációban  természetesen ez is meg fog jelenni.  
## JAXB  
* @XmlRootElement() - Olyan osztályra teendő rá, amelyet szeretnénk XML  formátumban kiírni, vagy abból beolvasni.  
* @XmlElement - Konfigurálható vele az XML tag neve.  
* @XmlAccessorType() - ha a JAXB annotációkat nem a getterre, hanem az attribútumra  akarjuk rátenni, akkor használjuk. 
## Spring Boot validáció beépített annotációk  
* @AssertTrue és @AssertFalse - Egy boolean típusú adat esetén lehet vizsgálni, hogy  true vagy false-e az értéke.  
* @Null és @NotNull - Referencia típusú adat esetén lehet megmondani, hogy az adott  mező értéke lehet-e null vagy nem.  
* @Size - String vagy kollekció esetén meg lehet határozni méretkorlátokat.  
* @Max, @Min, @Positive, @PositiveOrZero, @Negative, @NegativeOrZero, @DecimalMax,  @DecimalMin, @Digits - Egész- és lebegőpontos számoknál meg lehet határozni  minimum és maximum értékeket, elvárást az előjelre vonatkozóan, illetve azt is, hogy  hány számjegyből álljon.  
* @Future, @Past, @PastOrPresent, @FutureOrPresent - Különböző dátum és idő típusú  attribútumoknál meg lehet határozni, hogy az a múltban vagy a jövőben legyen.  
* @Pattern - Meg lehet határozni, hogy egy string feleljen meg egy reguláris kifejezésnek.  
* @Email - Meg lehet nézni egy stringről, hogy e-mail cím formátumú-e.  
* @NotEmpty - Meg lehet határozni, hogy egy string vagy kollekció nem lehet üres.  
* @NotBlank - Meg lehet határozni, hogy egy string nem lehet üres, null, illetve nem  tartalmazhat csupa whitespace karaktereket.  
* @Valid - Ahhoz, hogy a Spring automatikusan megvizsgálja a paramétereket (a Dto kat) az adott controller metódus meghívásakor, az átadott paraméter elé el kell  helyezni ezt az annotációt.  
* @Validated - Amennyiben egy osztályon szerepel ez az annotáció, akkor a Spring erre  az osztályra rá fogja futtatni a Bean Validationt.  
* @Constraint - Saját validációs annotáció létrehozásakor. Ahhoz, hogy az általunk  létrehozott új annotáció tényleg egy validációs annotáció legyen, rá kell tenni ezt az  annotációt, amelynek paraméterül meg kell adni a validálást elvégző osztály nevét.  
## Spring Boot konfiguráció  
* @Value - Konfigurációs értékek injektálására is használható. Paraméterként meg kell  adni a kívánt konfigurációs érték kulcsát.  
* @ConfigurationProperties - Ezt az annotációt egy olyan osztályra tesszük, melynek  attribútumait konfigurációból töltjük fel.  
* @EnableConfigurationProperties() - Az előző annotációval ellátott konfigurációs  osztályra kell ezzel az annotációval hivatkozni, ekkor példányosítja a Spring, az  `application.properties`-ben szereplő értékekkel feltöltve. 
## Adatbáziskezelés  
* @DataJpaTest - A tesztosztályra teendő, ha kizárólag a repository réteget akarjuk  tesztelni.  
* @Sql - A tesztosztályra teendő, és ilyenkor minden egyes tesztmetódus előtt le fog futni  az itt megadott SQL-utasítás. 