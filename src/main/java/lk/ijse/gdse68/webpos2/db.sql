create database webpos2;
use webpos2;
create table customer(
                         id VARCHAR(50) primary key,
                         username VARCHAR(50),
                         fullName VARCHAR(50),
                         street TEXT,
                         lane TEXT,
                         city TEXT,
                         email TEXT
);

create table item(
                         code VARCHAR(50) primary key,
                         item_name VARCHAR(255),
                         qty double,
                         price double,
                         description TEXT,
                         image TEXT
);




