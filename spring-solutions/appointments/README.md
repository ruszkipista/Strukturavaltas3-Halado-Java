Készíts a NAV-hoz egy időpontfoglaló rendszert!
Hozzd létre a `NavService` és a `NavController` osztályokat.

A `/api/types` címen le lehessen kérdezni az ügytípusokat, melyek kódok, és hozzá tartozó értékek!
Pl.: 001 - Adóbevallás, 002 - Befizetés, stb. 
Ezeket a `NavService` listájában tárold el (két attribútummal rendelkező objektumok, akár be is égetheted)!

Legyen a `NavService` osztályban még egy foglalt időpontok listája ami kezdetben üres.

Valósítsd meg az időpont foglalást mely az `/api/appointments/` címen legyen elérhető.

A következő adatokat várja egy command-ban:
* adóazonosító jel (CDV ellenőrzés: pontosan tíz számjegyet tartalmaz. Fogni kell az első kilenc számjegyet,
  és megszorozni rendre 1, 2, ..., 9 számmal. Az eredményt kell összegezni, majd maradékosan osztani 11-gyel.
  A 10. számjegynek meg kell egyeznie ezzel a számmal (maradékkal).)
  A `NavService`-ben mentés előtt ellenőrizd, ha nem oké dobj kivételt.
* időpont kezdete (jövőbeli időpontnak kell lennie, van rá beépített annotáció)
* időpont vége (jövőbeli időpontnak kell lennie)
* ügytípus azonosítója  

Ha minden oké, mentsd el az adatokat a foglalt időpontok listába.