Hozz létre egy új projektet a labs repódban `users-jpa-project` néven és ebbe a projektbe dolgozz!

Készíts egy `users.User` entitást. A felhasználónak legyen egy felhasználóneve, és egy jelszava egy `Role` enum típusú attribútuma, ami
lehet `ADMIN` vagy `USER`. Figyelj arra, hogy az adatbázisban a tábla és az oszlopk neve konvenciónak megfelelő legyen.

Készíts egy `users.UserRepository` nevű osztályt, ami az adatbázissal való kommunikációért felelős.
Ebben az osztályban legyen egy `saveUser(User user)` nevű metódust, ami elment egy felhasználót az adatbázisba. 
Legyen benne még egy `findUserById(long id)` és egy `findUserByUserName(String ussername)` metódus is. 
Végezetül legyen egy `updateUserPassword(String username, String newPassword)` metódus, ami megkeresi az adatbázisban
a felhasználót a neve alapján, majd frissíti a jelszavát. 