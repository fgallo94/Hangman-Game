# Hangman-Game
Hangman game Multi-threading.

## Pre-requisites

**Docker**

```Dockerfile
$ docker run -d -p 3306:3306 --name hangman-mysql -e MYSQL_ROOT_PASSWORD=1234 fgallo94/hangman-mysql

```
or use this scripts

```SQL
create database hangman;

use hangman;

create table Result(
    id integer not null auto_increment,
    name_of_winner varchar(100) not null,
    date_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
    char_used varchar(100) not null,
    PRIMARY key(id)
);

create table Word(
    id integer not null auto_increment,
    word varchar(100) not null,
    PRIMARY KEY(id)
);

INSERT INTO Word (word)
VALUES ('Carrefour');

INSERT INTO Word (word)
VALUES ('Toledo');

INSERT INTO Word (word)
VALUES ('Wallmart');
```
