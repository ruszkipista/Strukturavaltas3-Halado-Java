A feladatban szerzőket és általuk írt könyveket kell tárolnunk adatbázisban.

Entitások:

* `Author` (id, név, könyvek listája)
* `Book` (id, ISBN szám, cím, szerző)

Mint látható, a két entitás között kétirányú egy-több kapcsolat van. A könyvek táblájában a szerző id-ja külső kulcsként kell, hogy szerepeljen.

Szerzőt létre lehet hozni könyv nélkül, könyvet hozzárendelni add metódussal lehet, de figyeljünk, hogy a könyv szerzőjét állítsuk be! 

Könyvet ne lehessen szerző nélkül létrehozni!

Legyen külön üzleti logika réteg és adatbázis kezelő réteg is a szerzők és a könyvek számára is! Az adatbázis táblákat most 
elegendő, ha a Hibernate hozza létre, de külön eszközzel is megvalósíthatod ezt.

Az `AuthorController` alapértelmezetten az `/api/authors` végponton hallgatózik, a `BookController` az `/api/books` végponton.

* Lehessen szerzőt létrehozni, ekkor csak a szerző nevét várjuk!
* Lehessen könyvet létrehozni és a szerzőhöz hozzáadni a `/{id}/books` végponton. Ekkor a könyv ISBN számát és címét várjuk, valamint 
  a szerző id-ját!
* Lehessen egy szerzőt lekérdezni id alapján a `/{id}` végponton, ekkor a szerzőt az összes könyvével együtt kapjuk vissza!
* Lehessen egy könyvet lekérdezni cím alapján. A cím lehet teljes cím vagy csak egy címtöredék.
* Lehessen lekérdezni egy könyv ISBN számát a `/{id}/isbn` végponton! A kérésben a könyv id-ját várjuk.
