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
