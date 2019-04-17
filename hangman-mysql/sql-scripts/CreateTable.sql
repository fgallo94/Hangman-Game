create table Result(
    id integer not null auto_increment,
    name_of_winner varchar(100) not null,
    char_used integer not null,
    PRIMARY key(id)
);

create table Word(
    id integer not null auto_increment,
    word varchar(100) not null,
    PRIMARY KEY(id)
);
