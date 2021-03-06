CREATE DATABASE IF NOT EXISTS springbootdemo;
USE springbootdemo;

CREATE TABLE IF NOT EXISTS role
(
    id int NOT NULL AUTO_INCREMENT,
    title varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS operation_type
(
    id int NOT NULL AUTO_INCREMENT,
    title varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS currency
(
    id int NOT NULL AUTO_INCREMENT,
    title varchar(3) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS group_of_categories
(
    id int NOT NULL AUTO_INCREMENT,
    title varchar(20) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS category
(
    id int NOT NULL AUTO_INCREMENT,
    title varchar(20) NOT NULL,
    group_id int NOT NULL,
    foreign key (group_id) references group_of_categories (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS user
(
    id int NOT NULL AUTO_INCREMENT,
    first_name varchar(20) NOT NULL,
    second_name varchar(20) NOT NULL,
    username varchar (50) NOT NULL,
    password varchar (60) NOT NULL,
    role_id int NOT NULL,
    foreign key (role_id) references role (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS account
(
    id int NOT NULL AUTO_INCREMENT,
    number varchar(19) NOT NULL,
    balance bigint NOT NULL DEFAULT 0.00,
    user_id int NOT NULL,
    currency_id int NOT NULL,
    foreign key (currency_id) references currency (id),
    foreign key (user_id) references user (id),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS operation
(
    id int NOT NULL AUTO_INCREMENT,
    description varchar(100),
    date DATETIME NOT NULL,
    price bigint,
    operation_type_id int,
    category_id int,
    account_id int,
    transfer_to int,
    total_for_transfer bigint,
    foreign key (operation_type_id) references operation_type (id),
    foreign key (category_id) references category (id),
    foreign key (account_id) references account (id),
    foreign key (transfer_to) references account (id),
    PRIMARY KEY (id)
);