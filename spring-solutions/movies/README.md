A mai feladatban ismét egy filmekkel foglalkozó alkalmazást kell összeraknod.  

A `Movie` entitásnak legyen egy azonosítója, egy címe, egy hossza, egy listája az eddigi értékelésekkel, valamint egy értékelésátlaga.
Minden egyes alkalommal amikor egy értékelést kap a film, akkor az értékelésátlag ennek megfelelően változik!

Legyen egy `MovieService` osztályod, ami listában tárolja a filmeket. Kezdetben a lista üres, később tudunk filmet hozzáadni.   

Legyen egy `MovieController` amely alapértelmezetten az `/api/movies` URL-en várja a kéréseket.   

A következő funkciókat kell megvalósítani:

* az `/api/movies` végponton lehessen lekérni az összes filmet illetve új filmet hozzáadni (cím és hossz).
* a `/{id}` URL-en keresztül lehessen egy aktuális filmet lekérdezni.
* a `/{id}/ratings` URL-en keresztül lehessen egy filmre értékelést adni és az értékeléseit lekérdezni.
    - GET esetén adjuk vissza a film értékeléseinek listáját. 
    - POST esetén egy értékelés értéket várunk és az értékelések listájával térünk vissza.
* az `/api/movies/{namefragment}` végponton lehet keresni név töredek alapján filmeket
* az `/api/movies` végponton lehet módosítani egy film tulajdonságait: cím  és hossz
