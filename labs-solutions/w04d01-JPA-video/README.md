# Konzultáció - 2023.02.27

## Gyakorlófeladat

* Adottak a `User`, `Video`, `Tag`, és `Comment` entitások, ezeket kell egymással kapcsolatokba állítani 
  a következő módon: A `User`-eknek lehetnek videóik, a videók pedig kaphatnak `Tag`-eket. Ezenkívül 
  a `User`-ek `Comment`-eket is adhatnak.
  (Az entitások mindegyikéhez meg van írva a `save()` és a `find()` metódus, valamint az ezeket ellenőrző teszteset.)

* Ennek a felépítésnek a megvalósításához oldd meg az alábbi feladatokat!

  * Kapcsold össze a `User` és a `Video` osztályokat egy kétirányú, egy-több kapcsolattal!
    (egy felhasználóhoz több videó tartozhat, de egy videó csak egy felhasználóhoz tartozhat)
    Ehhez a következő lépéseket szükségesek:
    * Vedd fel a megfelelő kapcsolati attribútumokat mindkét osztályban és tedd ki a megfelelő 
      annotációkat!
    * Írd meg a megfelelő `add()` metódust a `User` osztályban!
    * Írd meg a megfelelő `saveVideoToUser()` metódust a `UserDao` osztályban!
    * Írd meg a megfelelő `findUserWithVideos()` metódust a `UserDao` osztályban!
    * Futtasd le a `UserDaoTest` `testFindUserWithVideos()` nevű metódusát, hogy ellenőrizd magad!
  
  * Kapcsold össze a `Video` és a `Tag` osztályokat egy egyirányú, több-több kapcsolattal!
    (egy videóhoz több tag tartozhat, és egy tag több videóhoz is tartozhat)
    Ehhez a következő lépéseket szükségesek:
    * Vedd fel a megfelelő kapcsolati attribútumot a `Video` osztályban és tedd ki a megfelelő
      annotációt!
    * Írd meg a megfelelő `add()` metódust a `Video` osztályban!
    * Írd meg a megfelelő `saveTagToVideo()` metódust a `VideoDao` osztályban!
    * Írd meg a megfelelő `findVideoWithTags()` metódust a `VideoDao` osztályban!
    * Futtasd le a `VideoDaoTest` `testFindVideoWithTags()` nevű metódusát, hogy ellenőrizd magad!

  * Kapcsold össze a `User` és a `Comment` osztályokat egy egyirányú, egy-több kapcsolattal!
    (egy felhasználóhoz több komment tartozhat, de egy komment csak egy felhasználóhoz tartozhat)
    Ehhez a következő lépéseket szükségesek:
    * Vedd fel a megfelelő kapcsolati attribútumot a `User` osztályban és tedd ki a megfelelő
      annotációt!
    * Írd meg a megfelelő `add()` metódust a `User` osztályban!
    * Írd meg a megfelelő `saveCommentToUser()` metódust a `UserDao` osztályban!
    * Írd meg a megfelelő `findUserWithComments()` metódust a `UserDao` osztályban!
    * Futtasd le a `UserDaoTest` `testFindUserWithComments()` nevű metódusát, hogy ellenőrizd magad!
