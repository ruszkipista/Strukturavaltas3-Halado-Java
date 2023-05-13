Hozz létre egy új projektet

A `trainings` csomagba dolgozz. Készíts egy `Trainer` nevű entitást. Attribútumai egy `String name` és egy `Status status`, ami egy felsorolásos típus `(JUNIOR,INTERMEDIATE,SENIOR)`. Ezeket az értékeket konstruktorban kapja meg.  
Készíts egy `Training` nevű entitást. Attribútumai: `String title`, `LocalDate startDate`, `LocalDate endDate`. A kapcsolat a kettő között, 
hogy egy oktató több traininget is vihet, de egy training csak egy oktatóhoz tartozik.

Készítsd el a `TrainersTrainingsRepository`-t. Ebben lehessen oktatókat lementeni. Lehessen tanfolyamot is lementeni, de csak úgy, hogy megkapjuk az oktató azonosítóját.

Írj egy metódust amivel egy oktatót lehet lekérdezni az összes olyan tanfolyamával ami egy adott időszakra esik.
`Trainer findTrainerWithTrainingsBetween(long trainerId, LocalDate startDate, LocalDate endDate)`.