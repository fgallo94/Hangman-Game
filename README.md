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
### Runnable vs Thread
Runnable it's an Interface and Thread it's a class, both are used on threads, you can implement the Runnable interface and override the run () method or extends of the Thread class, but you cannot extend from another class so implements Runnable is the preferred way to use threads.

### Life-Cycle of a Thread

State 	        | Description
----------------|--------------
New             | A new thread begins its life cycle in the new state. It remains in this state until the program starts the thread.
Runnable        | After a  thread is started, the thread becomes runnable. A thread in this state is considered to be executing its task.
Waiting         | A thread transitions to the waiting state while the thread waits for another thread to perform a task.
Timed Waiting   | A runnable thread can enter the timed waiting state for a specified interval of time. 
Terminated      | A runnable thread enters the terminated state when it completes its task or otherwise terminates.

### Docs of most used methods

Method              | Description
--------------------|------------
start()             | Starts the thread in a separate path of execution, then invokes the run() method on this Thread object.
yield()             | Causes the currently running thread to yield to any other threads of the same priority that are waiting to be scheduled.
sleep(long millisec)| Causes the currently running thread to block for at least the specified number of milliseconds.
join(long millisec) | The current thread invokes this method on a second thread, causing the current thread to block until the second thread terminates or the specified number of milliseconds passes.


