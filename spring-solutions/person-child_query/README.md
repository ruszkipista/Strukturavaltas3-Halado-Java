# Konzultáció - 2023.04.06.

## Gyakorlati feladat

Adott egy `Person` és egy `Child` entitás. A `Person`-nek van neve és életkora, a `Child`-nak neve és születési éve.
A két entitás között kétirányú egy-több kapcsolat van.
A feladat a megfelelő JPQL lekérdezések megírása az alábbiakra:

Keressük meg:

1. azokat a gyerekeket, akik egy megadott év után születtek
2. azokat a felnőtteket, akiknek több gyerekük is van
3. azt a felnőttet, akinek a legtöbb gyereke van
4. azt a gyereket, akinek a szülője a megadott nevű, és a megadott évben született (a gyerek) 
5. azt a szülőt, akihez a paraméterül megadott nevű gyerek tartozik
6. az átlagos gyerekszámot
7. azokat a gyerekeket, akik a legtöbben vannak testvérek

A megadott tesztekkel ellenőrizheted magad.
