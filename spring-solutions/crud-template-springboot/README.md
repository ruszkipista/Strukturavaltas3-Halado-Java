Hozz letre egy uj adatbazis semat:
```SQL
USE mysql;
CREATE SCHEMA IF NOT EXISTS crudtemplate DEFAULT CHARACTER SET utf8;
CREATE USER 'training'@'localhost' IDENTIFIED BY 'training';
GRANT ALL ON *.* TO 'training'@'localhost';
```